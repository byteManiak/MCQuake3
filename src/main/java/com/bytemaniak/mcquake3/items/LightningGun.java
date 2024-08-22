package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.registry.client.Renderers;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightningGun extends HitscanWeapon {
    private static final long LIGHTNING_REFIRE_RATE = 1;
    private static final float LIGHTNING_DAMAGE = MiscUtils.toMCDamage(8);
    private static final float LIGHTNING_RANGE = 30;

    public LightningGun() {
        super(new Identifier("mcquake3:lightning_gun"), LIGHTNING_REFIRE_RATE, LIGHTNING_REFIRE_RATE,
                false, null, true, LIGHTNING_DAMAGE, Q3DamageSources.LIGHTNING_DAMAGE,
                LIGHTNING_RANGE, Weapons.LIGHTNING_CELL, 100, 60, 5, false);
    }
    @Override
    protected void onDamage(World world, LivingEntity attacked) {
        if (attacked instanceof CreeperEntity creeper && !creeper.shouldRenderOverlay()) {
            world.playSoundFromEntity(null, creeper, SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.NEUTRAL, 1, 1);
            creeper.onStruckByLightning((ServerWorld) world, null);
        }
        world.playSoundFromEntity(null, attacked, Sounds.LIGHTNING_HIT, SoundCategory.NEUTRAL, .5f, 1);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        if (user.isSubmergedInWater()) {
            DamageSource selfDamage = Q3DamageSources.of(world, Q3DamageSources.LIGHTNING_DAMAGE_SELF, user, user);
            user.damage(selfDamage, LIGHTNING_DAMAGE*3);
            onDamage(world, user);
        } else super.onWeaponRefire(world, user, stack, lookDir, weaponPos);
    }

    @Override
    protected void onProjectileCollision(World world, LivingEntity user, Vec3d userPos, Vec3d iterPos, Vec3d upVec, boolean isBlockCollision) {
        if (world.isClient) submitLightningGunTrail(user, userPos, iterPos, upVec);
    }

    @Environment(EnvType.CLIENT)
    private void submitLightningGunTrail(LivingEntity user, Vec3d startPos, Vec3d endPos, Vec3d upVec) {
        Renderers.trailRenderer.addTrail(startPos, endPos, upVec, user.getUuid(), slot);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isCreative() || user.getInventory().getSlotWithStack(new ItemStack(Weapons.LIGHTNING_CELL)) > -1)
            world.playSoundFromEntity(null, user, Sounds.LIGHTNING_FIRE, SoundCategory.PLAYERS, 1, 1);

        return super.use(world, user, hand);
    }
}
