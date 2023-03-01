package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Gauntlet extends HitscanWeapon {
    private static final long GAUNTLET_REFIRE_RATE = 10;
    private static final int GAUNTLET_QUAKE_DAMAGE = 50;
    private static final int GAUNTLET_MC_DAMAGE = 5;
    private static final float GAUNTLET_RANGE = 1.5f;

    public Gauntlet() {
        super(MCuakePlayer.WeaponSlot.GAUNTLET, GAUNTLET_REFIRE_RATE, false, null, true,
                GAUNTLET_QUAKE_DAMAGE, GAUNTLET_MC_DAMAGE, GAUNTLET_RANGE, DamageSources.GAUNTLET_DAMAGE);
    }

    @Override
    protected void onQuakeDamage(World world, LivingEntity attacked) {
        world.playSoundFromEntity(null, attacked, Sounds.GAUNTLET_DAMAGE, SoundCategory.PLAYERS, 1, 1);
    }

    @Override
    protected void onMcDamage(World world, LivingEntity attacked) {
        onQuakeDamage(world, attacked);
    }

    @Override
    protected void onProjectileCollision(World world, Vec3d userPos, Vec3d iterPos) {}
}
