package com.bytemaniak.mcuake.entity;

import net.minecraft.entity.damage.DamageSource;

public interface MCuakePlayer {
    public void setQuakeHealth(int amount);
    public void setQuakeArmor(int amount);

    public int getQuakeHealth();
    public int getQuakeArmor();

    public void resetAmmo();

    public void takeDamage(int amount, DamageSource damageSource);
}
