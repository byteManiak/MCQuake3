package com.bytemaniak.mcuake.mixin;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.items.Weapon;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
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
public abstract class PlayerMixin extends LivingEntity implements MCuakePlayer {
    private static final float FALL_DISTANCE_MODIFIER = 4;

    private static final TrackedData<Integer> QUAKE_HEALTH = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> QUAKE_ARMOR = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> IN_QUAKE_MODE = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<String> QUAKE_PLAYER_SOUNDS = DataTracker.registerData(PlayerMixin.class, TrackedDataHandlerRegistry.STRING);

    // No point syncing ammo (for now?), so using a regular int array
    private int[] weaponAmmo = new int[10];

    private long[] weaponTicks = new long[9];
    private long[] clientWeaponTicks = new long[9];

    private Sounds.PlayerSounds playerSounds = Sounds.TONY;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public float reduceFallDistance(float fallDistance) {
        float newFallDistance = Float.max(0.f, fallDistance - FALL_DISTANCE_MODIFIER);
        if (isInQuakeMode() && newFallDistance >= 1) {
            takeDamage((int) newFallDistance, DamageSource.FALL);
            // Don't take Minecraft fall damage if in Quake mode
            return 0;
        }
        return newFallDistance;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void writeQuakeNbtData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("quake_health", getQuakeHealth());
        nbt.putInt("quake_armor", getQuakeArmor());
        nbt.putBoolean("quake_mode", isInQuakeMode());
        nbt.putString("quake_player_sounds", playerSounds.playerClass);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readQuakeNbtData(NbtCompound nbt, CallbackInfo ci) {
        this.dataTracker.set(QUAKE_HEALTH, nbt.getInt("quake_health"));
        this.dataTracker.set(QUAKE_ARMOR, nbt.getInt("quake_armor"));
        this.dataTracker.set(IN_QUAKE_MODE, nbt.getBoolean("quake_mode"));

        String quakePlayerSounds = nbt.getString("quake_player_sounds");
        this.dataTracker.set(QUAKE_PLAYER_SOUNDS, quakePlayerSounds);
        this.playerSounds = new Sounds.PlayerSounds(quakePlayerSounds);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    public void initQuakeDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(QUAKE_HEALTH, 100);
        this.dataTracker.startTracking(QUAKE_ARMOR, 0);
        this.dataTracker.startTracking(IN_QUAKE_MODE, false);
        this.dataTracker.startTracking(QUAKE_PLAYER_SOUNDS, "Tony");
    }

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    private void noDropInventoryInQuakeMode(CallbackInfo ci) {
        if (this.isInQuakeMode()) ci.cancel();
    }

    @Override
    public void toggleQuakeMode() {
        boolean newMode = !this.dataTracker.get(IN_QUAKE_MODE);
        this.dataTracker.set(IN_QUAKE_MODE, newMode);
    }

    @Override
    public boolean isInQuakeMode() { return this.dataTracker.get(IN_QUAKE_MODE); }

    @Override
    public void setQuakeMode(boolean enabled) { this.dataTracker.set(IN_QUAKE_MODE, enabled); }

    @Override
    public int getQuakeHealth() { return this.dataTracker.get(QUAKE_HEALTH); }

    @Override
    public int getQuakeArmor() { return this.dataTracker.get(QUAKE_ARMOR); }

    @Override
    public void setQuakeHealth(int amount) {
        this.dataTracker.set(QUAKE_HEALTH, amount);
    }

    @Override
    public void setQuakeArmor(int amount) { this.dataTracker.set(QUAKE_ARMOR, amount); }

    @Override
    public void takeDamage(int amount, DamageSource damageSource) {
        int quakeHealth = getQuakeHealth();
        quakeHealth -= amount;
        if (quakeHealth <= 0) {
            this.damage(damageSource, Integer.MAX_VALUE);
            resetAmmo();
            // Reset weapon ticks so weapon delay doesn't apply to the newly-spawned player
            weaponTicks = new long[9];
            clientWeaponTicks = new long[9];
            setQuakeHealth(100);
            world.playSoundFromEntity(null, this, SoundEvent.of(playerSounds.DEATH), SoundCategory.PLAYERS, 1, 1);
        } else {
            SoundEvent hurtSound;
            setQuakeHealth(quakeHealth);
            if (damageSource.isFromFalling()) {
                hurtSound = SoundEvent.of(playerSounds.FALL);
            } else {
                if (quakeHealth > 75) hurtSound = SoundEvent.of(playerSounds.HURT100);
                else if (quakeHealth > 50) hurtSound = SoundEvent.of(playerSounds.HURT75);
                else if (quakeHealth > 25) hurtSound = SoundEvent.of(playerSounds.HURT50);
                else hurtSound = SoundEvent.of(playerSounds.HURT25);
            }
            world.playSoundFromEntity(null, this, hurtSound, SoundCategory.PLAYERS, 1, 1);
        }
    }

    @Override
    public long getWeaponTick(WeaponSlot slot, boolean clientside) {
        return clientside ? clientWeaponTicks[slot.slot()] : weaponTicks[slot.slot()];
    }

    @Override
    public void setWeaponTick(WeaponSlot slot, long tick, boolean clientside) {
        if (clientside) clientWeaponTicks[slot.slot()] = tick;
        else weaponTicks[slot.slot()] = tick;
    }

    @Override
    public void resetAmmo() {
        weaponAmmo = new int[10];
        weaponAmmo[WeaponSlot.MACHINEGUN.slot()] = 100;
        weaponAmmo[WeaponSlot.PLASMA_GUN.slot()] = 50;
        weaponAmmo[WeaponSlot.RAILGUN.slot()] = 10;
    }

    @Override
    public boolean useAmmo(WeaponSlot slot) {
        if (weaponAmmo[slot.slot()] > 0) {
            weaponAmmo[slot.slot()]--;
            return false;
        } else {
            weaponAmmo[slot.slot()] = 50;
            return true;
        }
    }

    @Override
    public WeaponSlot getCurrentWeapon() {
        if (getMainHandStack().getItem() instanceof Weapon weapon) {
            return weapon.weaponSlot;
        } else {
            return WeaponSlot.NONE;
        }
    }

    @Override
    public int getCurrentAmmo() {
        return weaponAmmo[getCurrentWeapon().slot()];
    }

    @Override
    public void setPlayerSounds(String soundsSet) { this.playerSounds = new Sounds.PlayerSounds(soundsSet); }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void cancelMobInteract(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (getMainHandStack().getItem() instanceof Weapon) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
