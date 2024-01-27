package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.client.Renderers;
import com.bytemaniak.mcquake3.util.MiscUtils;
import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
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
        super(WeaponSlot.LIGHTNING_GUN, new Identifier("mcquake3:lightning_gun"),
                LIGHTNING_REFIRE_RATE, false, null, true,
                LIGHTNING_DAMAGE, Q3DamageSources.LIGHTNING_DAMAGE, LIGHTNING_RANGE);
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
    protected void onProjectileCollision(World world, LivingEntity user, Vec3d userPos, Vec3d iterPos, boolean isBlockCollision) {
        if (world.isClient)
            submitLightningGunTrail(user, userPos, iterPos);
    }

    @Environment(EnvType.CLIENT)
    private void submitLightningGunTrail(LivingEntity user, Vec3d startPos, Vec3d endPos) {
        Renderers.trailRenderer.addTrail(startPos, endPos, user.getUuid(), WeaponSlot.LIGHTNING_GUN.slot);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.playSoundFromEntity(null, user, Sounds.LIGHTNING_FIRE, SoundCategory.PLAYERS, 1, 1);

        return super.use(world, user, hand);
    }
}
