package com.bytemaniak.mcuake.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ExplosiveProjectileEntity.class)
public abstract class ExplosiveProjectileEntityMixin extends ProjectileEntity {
    public ExplosiveProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyArgs(method = "tick", at = @At(value = "INVOKE", target="Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
    // The ExplosiveProjectileEntity class spawns smoke particles by default which are offset a bit higher
    // from the projectile itself. Lower it so that the MCuake projectiles look like they leave a trail.
    // It also spawns bubbles when underwater, which we don't want to modify
    private void reduceParticleHeight(Args args) {
        ParticleType type = args.get(0);
        if (type != ParticleTypes.BUBBLE && type != ParticleTypes.SMOKE) {
            double y = args.get(2);
            args.set(2, y - 0.5);
        }
    }
}
