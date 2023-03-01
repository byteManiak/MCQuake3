package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Machinegun extends HitscanWeapon {
    private static final long MACHINEGUN_REFIRE_TICK_RATE = 2;
    private static final int MACHINEGUN_QUAKE_DAMAGE = 7;
    private static final int MACHINEGUN_MC_DAMAGE = 2;
    private static final float MACHINEGUN_RANGE = 200;

    public Machinegun() {
        super(MCuakePlayer.WeaponSlot.MACHINEGUN, MACHINEGUN_REFIRE_TICK_RATE, true, Sounds.MACHINEGUN_FIRE, false,
                MACHINEGUN_QUAKE_DAMAGE, MACHINEGUN_MC_DAMAGE, MACHINEGUN_RANGE, DamageSources.MACHINEGUN_DAMAGE);
    }

    @Override
    protected void onQuakeDamage(World world, LivingEntity attacked) {}

    @Override
    protected void onMcDamage(World world, LivingEntity attacked) {}

    @Override
    protected void onProjectileCollision(World world, Vec3d userPos, Vec3d iterPos) {}
}
