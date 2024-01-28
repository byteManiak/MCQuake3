package com.bytemaniak.mcquake3.mixin;

import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity implements QuakePlayer {
    private static final float FALL_DISTANCE_MODIFIER = 4;

    private static final TrackedData<Boolean> QUAKE_GUI_ENABLED = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<String> QUAKE_PLAYER_SOUNDS = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.STRING);

    private final static TrackedData<Integer> QUAKE_ARMOR = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.INTEGER);

    private final long[] weaponTicks = new long[9];

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V"))
    public void playWeaponSwitchSound(PlayerEntity playerEntity, Operation<Void> original) {
        if (!world.isClient && playerEntity.getMainHandStack().getItem() instanceof Weapon)
            world.playSoundFromEntity(null, this, Sounds.WEAPON_CHANGE, SoundCategory.NEUTRAL, 1, 1);
        original.call(playerEntity);
    }

    @ModifyVariable(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public float reduceFallDistance(float fallDistance) {
        return Float.max(0.f, fallDistance - FALL_DISTANCE_MODIFIER);
    }

    @Inject(method = "jump", at = @At("HEAD"))
    private void playQuakeJumpSound(CallbackInfo ci) {
        if (quakePlayerSoundsEnabled()) {
            Sounds.PlayerSounds playerSounds = new Sounds.PlayerSounds(getPlayerVoice());
            world.playSoundFromEntity(null, this, SoundEvent.of(playerSounds.JUMP), SoundCategory.PLAYERS, 1, 1);
        }
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        if (quakePlayerSoundsEnabled()) {
            Sounds.PlayerSounds playerSounds = new Sounds.PlayerSounds(getPlayerVoice());
            if (source.isOf(DamageTypes.FALL)) return SoundEvent.of(playerSounds.FALL);
            else if (source.isOf(DamageTypes.DROWN)) return SoundEvent.of(playerSounds.DROWN);
            else if (getHealth() >= 15) return SoundEvent.of(playerSounds.HURT100);
            else if (getHealth() >= 10) return SoundEvent.of(playerSounds.HURT75);
            else if (getHealth() >= 5) return SoundEvent.of(playerSounds.HURT50);
            else return SoundEvent.of(playerSounds.HURT25);
        }
        return super.getHurtSound(source);
    }

    @Override
    public SoundEvent getDeathSound() {
        if (quakePlayerSoundsEnabled()) {
            Sounds.PlayerSounds playerSounds = new Sounds.PlayerSounds(getPlayerVoice());
            return SoundEvent.of(playerSounds.DEATH);
        }
        return super.getDeathSound();
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void writeQuakeNbtData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("quake_gui_enabled", quakeGuiEnabled());
        nbt.putString("quake_player_sounds", getPlayerVoice());
        nbt.putInt("quake_energy_shield", getEnergyShield());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readQuakeNbtData(NbtCompound nbt, CallbackInfo ci) {
        dataTracker.set(QUAKE_GUI_ENABLED, nbt.getBoolean("quake_gui_enabled"));
        dataTracker.set(QUAKE_ARMOR, nbt.getInt("quake_energy_shield"));

        String quakePlayerSounds = nbt.getString("quake_player_sounds");
        setPlayerVoice(quakePlayerSounds);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    public void initQuakeDataTracker(CallbackInfo ci) {
        dataTracker.startTracking(QUAKE_GUI_ENABLED, false);
        dataTracker.startTracking(QUAKE_PLAYER_SOUNDS, "Vanilla");
        dataTracker.startTracking(QUAKE_ARMOR, 0);
    }

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    private void noDropInventoryInQuakeMode(CallbackInfo ci) {
        if (quakeGuiEnabled()) ci.cancel();
    }

    @ModifyVariable(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("RETURN"), ordinal = 0, argsOnly = true)
    private ItemStack stopWeaponAnimation(ItemStack stack) {
        if (stack.getItem() instanceof Weapon) {
            stack.getOrCreateNbt().putDouble("firing_speed", 0.0);
        }
        return stack;
    }

    public void toggleQuakeGui() {
        boolean guiMode = !dataTracker.get(QUAKE_GUI_ENABLED);
        dataTracker.set(QUAKE_GUI_ENABLED, guiMode);
    }

    public boolean quakeGuiEnabled() { return dataTracker.get(QUAKE_GUI_ENABLED); }
    public void setQuakeGui(boolean enabled) { dataTracker.set(QUAKE_GUI_ENABLED, enabled); }

    public boolean quakePlayerSoundsEnabled() { return !dataTracker.get(QUAKE_PLAYER_SOUNDS).equals("Vanilla"); }

    public long getWeaponTick(int id) { return weaponTicks[id]; }

    public void setWeaponTick(int id, long tick) {
        weaponTicks[id] = tick;
    }

    public int getCurrentQuakeWeaponId() {
        if (getMainHandStack().getItem() instanceof Weapon weapon) {
            return weapon.slot;
        } else {
            return -1;
        }
    }

    public int getEnergyShield() { return dataTracker.get(QUAKE_ARMOR); }
    public void setEnergyShield(int amount) {
        dataTracker.set(QUAKE_ARMOR, amount);
    }
    public void addEnergyShield(int amount) {
        int currentShield = dataTracker.get(QUAKE_ARMOR);
        currentShield += amount; if (currentShield > 200) currentShield = 200;
        dataTracker.set(QUAKE_ARMOR, currentShield);
    }

    public String getPlayerVoice() { return dataTracker.get(QUAKE_PLAYER_SOUNDS); }
    public void setPlayerVoice(String soundsSet) {
        dataTracker.set(QUAKE_PLAYER_SOUNDS, soundsSet);
    }

    public void taunt() {
        if (quakePlayerSoundsEnabled()) {
            Sounds.PlayerSounds playerSounds = new Sounds.PlayerSounds(getPlayerVoice());
            world.playSoundFromEntity(null, this, SoundEvent.of(playerSounds.TAUNT), SoundCategory.NEUTRAL, 1, 1);
        }
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void cancelMobInteract(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (getMainHandStack().getItem() instanceof Weapon) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
