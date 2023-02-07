package com.bytemaniak.mcuake.mixin;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.items.Weapon;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity implements MCuakePlayer {
    @Shadow public abstract boolean damage(DamageSource source, float amount);

    private static final float FALL_DISTANCE_MODIFIER = 4;

    private int quakeHealth = 100;
    private int quakeArmor = 0;

    private int[] weaponAmmo = new int[10];

    private long[] weaponTicks = new long[9];
    private long[] clientWeaponTicks = new long[9];

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public float reduceFallDistance(float fallDistance) {
        return Float.max(0.f, fallDistance - FALL_DISTANCE_MODIFIER);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void writeQuakeNbtData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("quake_health", quakeHealth);
        nbt.putInt("quake_armor", quakeArmor);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readQuakeNbtData(NbtCompound nbt, CallbackInfo ci) {
        quakeHealth = nbt.getInt("quake_health");
        quakeArmor = nbt.getInt("quake_armor");
    }

    @Override
    public int getQuakeHealth() {
        return quakeHealth;
    }

    @Override
    public int getQuakeArmor() {
        return quakeArmor;
    }

    @Override
    public void setQuakeHealth(int amount) {
        quakeHealth = amount;
    }

    @Override
    public void setQuakeArmor(int amount) {
        quakeArmor = amount;
    }

    @Override
    public void takeDamage(int amount, DamageSource damageSource) {
        quakeHealth -= amount;
        if (quakeHealth <= 0) {
            this.damage(damageSource, Integer.MAX_VALUE);
            resetAmmo();
            // Reset weapon ticks so weapon delay doesn't apply to the newly-spawned player
            weaponTicks = new long[9];
            clientWeaponTicks = new long[9];
            quakeHealth = 100;
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
    public int getCurrentAmmo() { return weaponAmmo[getCurrentWeapon().slot()]; }
}
