package com.bytemaniak.mcquake3.mixin;

import com.bytemaniak.mcquake3.entity.PortalEntity;
import com.bytemaniak.mcquake3.items.Gauntlet;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.Blocks;
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
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
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
    @Shadow public abstract PlayerInventory getInventory();
    @Shadow public abstract void sendMessage(Text message, boolean overlay);
    @Shadow public abstract boolean isCreative();
    @Shadow public abstract boolean isSpectator();

    private static final float FALL_DISTANCE_MODIFIER = 4;

    private static final TrackedData<String> QUAKE_PLAYER_SOUNDS = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.STRING);

    private final static TrackedData<Integer> QUAKE_ARMOR = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.INTEGER);

    private final static TrackedData<Boolean> HAS_QL_REFIRE_RATE = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final long[] weaponTicks = new long[9];

    private final static long TIME_BETWEEN_HURTS = 9;
    private long lastHurtTick = 0;

    private int portalToLink = -1;

    private String toolMapName = "";
    private int mapToolMode;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V"))
    public void playWeaponSwitchSound(PlayerEntity playerEntity, Operation<Void> original) {
        if (!getWorld().isClient && playerEntity.getMainHandStack().getItem() instanceof Weapon)
            getWorld().playSoundFromEntity(null, this, Sounds.WEAPON_CHANGE, SoundCategory.NEUTRAL, 1, 1);
        original.call(playerEntity);
    }

    @ModifyVariable(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public float reduceFallDistance(float fallDistance) {
        return Float.max(0.f, fallDistance - FALL_DISTANCE_MODIFIER);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void cancelMobInteract(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (getMainHandStack().getItem() instanceof Weapon)
            cir.setReturnValue(ActionResult.PASS);
    }

    @Inject(method = "jump", at = @At("HEAD"))
    private void playQuakeJumpSound(CallbackInfo ci) {
        if (quakePlayerSoundsEnabled()) {
            Sounds.PlayerSounds playerSounds = new Sounds.PlayerSounds(getPlayerVoice());
            getWorld().playSoundFromEntity(null, this, SoundEvent.of(playerSounds.JUMP), SoundCategory.PLAYERS, 1, 1);
        }
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        if (quakePlayerSoundsEnabled()) {
            Sounds.PlayerSounds playerSounds = new Sounds.PlayerSounds(getPlayerVoice());
            if (source.isOf(DamageTypes.FALL)) return SoundEvent.of(playerSounds.FALL);
            else if (source.isOf(DamageTypes.DROWN)) return SoundEvent.of(playerSounds.DROWN);
            else {
                long currentTick = getWorld().getTime();
                if (currentTick - lastHurtTick >= TIME_BETWEEN_HURTS) {
                    lastHurtTick = currentTick;
                    if (isSubmergedIn(FluidTags.WATER)) return SoundEvent.of(playerSounds.DROWN);
                    else if (getHealth() >= 15) return SoundEvent.of(playerSounds.HURT100);
                    else if (getHealth() >= 10) return SoundEvent.of(playerSounds.HURT75);
                    else if (getHealth() >= 5) return SoundEvent.of(playerSounds.HURT50);
                    else return SoundEvent.of(playerSounds.HURT25);
                } else return null;
            }
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
        nbt.putString("quake_player_sounds", getPlayerVoice());
        nbt.putInt("quake_energy_shield", getEnergyShield());
        nbt.putBoolean("has_ql_refire_rate", hasQLRefireRate());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readQuakeNbtData(NbtCompound nbt, CallbackInfo ci) {
        dataTracker.set(QUAKE_ARMOR, nbt.getInt("quake_energy_shield"));
        dataTracker.set(HAS_QL_REFIRE_RATE, nbt.getBoolean("has_ql_refire_rate"));

        String quakePlayerSounds = nbt.getString("quake_player_sounds");
        setPlayerVoice(quakePlayerSounds);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    public void initQuakeDataTracker(CallbackInfo ci) {
        dataTracker.startTracking(QUAKE_PLAYER_SOUNDS, "Vanilla");
        dataTracker.startTracking(QUAKE_ARMOR, 0);
        dataTracker.startTracking(HAS_QL_REFIRE_RATE, false);
    }

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    private void noDropInventoryInQuakeMap(CallbackInfo ci) {
        if (playingQuakeMap()) ci.cancel();
    }

    @ModifyVariable(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("RETURN"), ordinal = 0, argsOnly = true)
    private ItemStack stopWeaponAnimation(ItemStack stack) {
        if (stack.getItem() instanceof Weapon)
            stack.getOrCreateNbt().putDouble("firing_speed", 0.0);
        return stack;
    }

    public boolean playingQuakeMap() {
        return getWorld().getDimensionKey() == Blocks.Q3_DIMENSION_TYPE && !isCreative() && !isSpectator();
    }

    public boolean quakePlayerSoundsEnabled() { return !dataTracker.get(QUAKE_PLAYER_SOUNDS).equals("Vanilla"); }

    public long getWeaponTick(int id) { return weaponTicks[id]; }
    public void setWeaponTick(int id, long tick) {
        weaponTicks[id] = tick;
    }
    public boolean hasQLRefireRate() { return dataTracker.get(HAS_QL_REFIRE_RATE); }
    public void setQLRefireRate(boolean hasQLRefire) { dataTracker.set(HAS_QL_REFIRE_RATE, hasQLRefire); }

    // Scroll to the next available slot in the hotbar in case
    // the currently held weapon has run out of ammo
    public void scrollToNextSuitableSlot() {
        PlayerInventory inv = getInventory();
        int slot = inv.selectedSlot;
        int nextSlot = slot;

        search: do {
            inv.scrollInHotbar(-1);
            nextSlot = (nextSlot+1)%9;

            ItemStack stack = inv.getMainHandStack();
            Item item = stack.getItem();
            if (stack.isOf(Items.AIR)) continue;
            if (!(item instanceof Weapon) || item instanceof Gauntlet) break;

            for (int i = 0; i < inv.size(); ++i) {
                ItemStack itemStack = inv.getStack(i);
                if (itemStack.isOf(((Weapon)item).ammoType)) break search;
            }
        } while (nextSlot != slot);
    }

    public int getCurrentQuakeWeaponId() {
        if (getMainHandStack().getItem() instanceof Weapon weapon) return weapon.slot;
        return -1;
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
            getWorld().playSoundFromEntity(null, this, SoundEvent.of(playerSounds.TAUNT), SoundCategory.NEUTRAL, 1, 1);
        }
    }

    @Override
    public void setPortalToLink(PortalEntity entity) {
        int x = entity.getBlockX();
        int y = entity.getBlockY();
        int z = entity.getBlockZ();
        sendMessage(Text.of("Portal ["+x+", "+y+", "+z+"] link started"), true);
        portalToLink = entity.getId();
    }

    @Override
    public void setLinkedPortalCoords() {
        if (portalToLink == -1) return;

        Entity entity = getWorld().getEntityById(portalToLink);
        if (entity instanceof PortalEntity portalEntity) {
            int px = portalEntity.getBlockX();
            int py = portalEntity.getBlockY();
            int pz = portalEntity.getBlockZ();
            int x = getBlockX();
            int y = getBlockY();
            int z = getBlockZ();

            portalEntity.setActive(true);
            portalEntity.setTeleportCoords(x, y, z);
            portalEntity.setTeleportFacing(getHorizontalFacing());
            sendMessage(Text.of("Portal ["+px+", "+py+", "+pz+"] linked to ["+x+", "+y+", "+z+"], facing "+getHorizontalFacing().asString()), true);
            portalToLink = -1;
        }
    }

    @Override
    public void setMapToolName(String mapName) { toolMapName = mapName; }

    @Override
    public String getMapToolName() { return toolMapName; }

    @Override
    public void setMapToolMode(int mode) { mapToolMode = mode; }

    @Override
    public int getMapToolMode() { return mapToolMode; }
}
