package com.bytemaniak.mcquake3.mixin;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.Items;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.sound.WeaponActive;
import com.bytemaniak.mcquake3.sound.WeaponHum;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity implements QuakePlayer {
    @Shadow public abstract boolean isCreative();

    private static final float FALL_DISTANCE_MODIFIER = 4;

    private static final int[] defaultWeaponAmmo = {
            0, 100, 10, 10, 5, 100, 10, 50, 20, 0
    };

    private static final TrackedData<Boolean> QUAKE_GUI_ENABLED = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<String> QUAKE_PLAYER_SOUNDS = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.STRING);

    // No point syncing ammo (for now?), so using a regular int array
    private final int[] weaponAmmo = new int[10];

    private final long[] weaponTicks = new long[9];

    private Sounds.PlayerSounds playerSounds = Sounds.TONY;

    private boolean isHoldingGauntlet = false;
    private boolean isHoldingLightning = false;
    private boolean isHoldingRailgun = false;

    private boolean playingHumSound = false;
    private boolean playingAttackSound = false;

    private ItemStack prevMainHandStack = null;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getMainHandStack() {
        ItemStack currentMainHandStack = getEquippedStack(EquipmentSlot.MAINHAND);
        if (!world.isClient && currentMainHandStack.getItem() instanceof Weapon && currentMainHandStack != prevMainHandStack) {
            world.playSoundFromEntity(null, this, Sounds.WEAPON_CHANGE, SoundCategory.NEUTRAL, 1, 1);
        }
        prevMainHandStack = currentMainHandStack;
        return super.getMainHandStack();
    }

    @ModifyVariable(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public float reduceFallDistance(float fallDistance) {
        return Float.max(0.f, fallDistance - FALL_DISTANCE_MODIFIER);
    }

    @Inject(method = "jump", at = @At("HEAD"))
    private void playQuakeJumpSound(CallbackInfo ci) {
        if (quakeGuiEnabled()) world.playSoundFromEntity(null, this, SoundEvent.of(playerSounds.JUMP), SoundCategory.PLAYERS, 1, 1);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void writeQuakeNbtData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("quake_gui_enabled", quakeGuiEnabled());
        nbt.putString("quake_player_sounds", playerSounds.playerClass);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readQuakeNbtData(NbtCompound nbt, CallbackInfo ci) {
        this.dataTracker.set(QUAKE_GUI_ENABLED, nbt.getBoolean("quake_gui_enabled"));

        String quakePlayerSounds = nbt.getString("quake_player_sounds");
        this.dataTracker.set(QUAKE_PLAYER_SOUNDS, quakePlayerSounds);
        this.playerSounds = new Sounds.PlayerSounds(quakePlayerSounds);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    public void initQuakeDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(QUAKE_GUI_ENABLED, false);
        this.dataTracker.startTracking(QUAKE_PLAYER_SOUNDS, "Tony");
    }

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    private void noDropInventoryInQuakeMode(CallbackInfo ci) {
        if (this.quakeGuiEnabled()) ci.cancel();
    }

    @ModifyVariable(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("RETURN"), ordinal = 0, argsOnly = true)
    private ItemStack stopWeaponAnimation(ItemStack stack) {
        if (stack.getItem() instanceof Weapon) {
            stack.getOrCreateNbt().putDouble("firing_speed", 0.0);
        }
        return stack;
    }

    public void toggleQuakeGui() {
        boolean guiMode = !this.dataTracker.get(QUAKE_GUI_ENABLED);
        this.dataTracker.set(QUAKE_GUI_ENABLED, guiMode);
    }

    public boolean quakeGuiEnabled() { return this.dataTracker.get(QUAKE_GUI_ENABLED); }
    public void setQuakeGui(boolean enabled) { this.dataTracker.set(QUAKE_GUI_ENABLED, enabled); }

    public long getWeaponTick(WeaponSlot slot) {
        return weaponTicks[slot.slot()];
    }

    public void setWeaponTick(WeaponSlot slot, long tick) {
        weaponTicks[slot.slot()] = tick;
    }

    public void resetAmmo() {
        System.arraycopy(defaultWeaponAmmo, 0, weaponAmmo, 0, weaponAmmo.length);
    }

    public boolean useAmmo(WeaponSlot slot) {
        if (slot == WeaponSlot.GAUNTLET) return false;

        if (weaponAmmo[slot.slot()] > 0) {
            weaponAmmo[slot.slot()]--;
            return false;
        } else {
            weaponAmmo[slot.slot()] = defaultWeaponAmmo[slot.slot()];
            return true;
        }
    }

    public WeaponSlot getCurrentWeapon() {
        if (getMainHandStack().getItem() instanceof Weapon weapon) {
            return weapon.weaponSlot;
        } else {
            return WeaponSlot.NONE;
        }
    }

    public int getCurrentAmmo() {
        return weaponAmmo[getCurrentWeapon().slot()];
    }

    public String getPlayerVoice() { return this.playerSounds.playerClass; }
    public void setPlayerVoice(String soundsSet) { this.playerSounds = new Sounds.PlayerSounds(soundsSet); }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void cancelMobInteract(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (getMainHandStack().getItem() instanceof Weapon) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void handleLoopingWeaponSounds(CallbackInfo ci) {
        if (world.isClient) {
            ItemStack handStack = getMainHandStack();
            if (handStack.getItem() instanceof Weapon weapon) {
                if (handStack.isOf(Items.GAUNTLET)) {
                    if (!isHoldingGauntlet) {
                        isHoldingGauntlet = true;
                        playHum(QuakePlayer.WeaponSlot.GAUNTLET);

                        isHoldingLightning = false;
                        isHoldingRailgun = false;
                    }
                } else if (handStack.isOf(Items.LIGHTNING_GUN)) {
                    if (!isHoldingLightning) {
                        isHoldingLightning = true;
                        playHum(QuakePlayer.WeaponSlot.LIGHTNING_GUN);

                        isHoldingGauntlet = false;
                        isHoldingRailgun = false;
                    }
                } else if (handStack.isOf(Items.RAILGUN)) {
                    if (!isHoldingRailgun) {
                        isHoldingRailgun = true;
                        playHum(QuakePlayer.WeaponSlot.RAILGUN);

                        isHoldingGauntlet = false;
                        isHoldingLightning = false;
                    }
                } else {
                    isHoldingGauntlet = false;
                    isHoldingRailgun = false;
                    isHoldingLightning = false;
                    stopSounds();
                }

                if (weapon.hasActiveLoopSound) {
                    if (!isUsingItem() && playingAttackSound) {
                        playHum(getCurrentWeapon());
                    } else if (isUsingItem() && !playingAttackSound) {
                        playAttackSound(getCurrentWeapon());
                    }
                }
            } else if (playingHumSound || playingAttackSound) {
                isHoldingGauntlet = false;
                isHoldingRailgun = false;
                isHoldingLightning = false;
                stopSounds();
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void playHum(QuakePlayer.WeaponSlot weaponSlot) {
        stopSounds();

        SoundManager manager = MinecraftClient.getInstance().getSoundManager();
        WeaponHum humSound;
        switch (weaponSlot) {
            case GAUNTLET -> humSound = new WeaponHum(this, Sounds.GAUNTLET_HUM, weaponSlot);
            case RAILGUN -> humSound = new WeaponHum(this, Sounds.RAILGUN_HUM, weaponSlot);
            default -> humSound = null;
        }

        if (humSound != null) {
            manager.play(humSound);
            playingHumSound = true;
        }
    }

    @Environment(EnvType.CLIENT)
    public void stopHum() { playingHumSound = false; }

    @Environment(EnvType.CLIENT)
    public void playAttackSound(QuakePlayer.WeaponSlot weaponSlot) {
        stopSounds();

        SoundManager manager = MinecraftClient.getInstance().getSoundManager();
        WeaponActive attackSound;
        switch (weaponSlot) {
            case GAUNTLET -> attackSound = new WeaponActive(this, Sounds.GAUNTLET_ACTIVE, weaponSlot);
            case LIGHTNING_GUN -> attackSound = new WeaponActive(this, Sounds.LIGHTNING_ACTIVE, weaponSlot);
            default -> attackSound = null;
        }

        if (attackSound != null) {
            manager.play(attackSound);
            playingAttackSound = true;
        }
    }

    @Environment(EnvType.CLIENT)
    public void stopAttackSound() { playingAttackSound = false; }

    @Environment(EnvType.CLIENT)
    public void stopSounds() {
        stopHum();
        stopAttackSound();
    }

    @Environment(EnvType.CLIENT)
    public boolean isPlayingHum() { return playingHumSound; }

    @Environment(EnvType.CLIENT)
    public boolean isPlayingAttack() { return playingAttackSound; }
}
