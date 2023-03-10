package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Packets;
import com.bytemaniak.mcuake.registry.Sounds;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Railgun extends HitscanWeapon implements GeoItem {
    private static final int RAILGUN_REFIRE_TICK_RATE = 50;
    private static final int RAILGUN_QUAKE_DAMAGE = 100;
    private static final int RAILGUN_MC_DAMAGE = 10;
    private static final float RAILGUN_RANGE = 200;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public Railgun() {
        super(QuakePlayer.WeaponSlot.RAILGUN, RAILGUN_REFIRE_TICK_RATE, true, Sounds.RAILGUN_FIRE, false,
                RAILGUN_QUAKE_DAMAGE, RAILGUN_MC_DAMAGE, DamageSources.RAILGUN_DAMAGE, RAILGUN_RANGE);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    protected void onProjectileCollision(World world, Vec3d userPos, Vec3d iterPos) {
        sendRailgunTrail(world, userPos, iterPos);
    }

    private void sendRailgunTrail(World world, Vec3d startPos, Vec3d endPos) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(startPos.x);
        buf.writeDouble(startPos.y);
        buf.writeDouble(startPos.z);
        buf.writeDouble(endPos.x);
        buf.writeDouble(endPos.y);
        buf.writeDouble(endPos.z);
        buf.writeInt(QuakePlayer.WeaponSlot.RAILGUN.slot());
        for (ServerPlayerEntity plr : PlayerLookup.world((ServerWorld) world))
            ServerPlayNetworking.send(plr, Packets.SHOW_TRAIL, buf);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "controller", "shoot");
        super.onWeaponRefire(world, user, stack);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> {
            state.getController().setAnimation(DefaultAnimations.IDLE);
            return PlayState.CONTINUE;
        }).triggerableAnim("shoot", DefaultAnimations.ATTACK_SHOOT)
          .triggerableAnim("idle", DefaultAnimations.IDLE));
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GeoItemRenderer<Railgun> renderer =
                    new GeoItemRenderer<>(new DefaultedItemGeoModel<Railgun>(new Identifier("mcuake", "railgun")));

            @Override
            public GeoItemRenderer<Railgun> getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public Supplier<Object> getRenderProvider() { return renderProvider; }
}
