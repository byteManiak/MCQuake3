package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.items.client.GauntletRenderer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Gauntlet extends HitscanWeapon implements GeoItem {
    private static final long GAUNTLET_REFIRE_RATE = 10;
    private static final int GAUNTLET_QUAKE_DAMAGE = 50;
    private static final int GAUNTLET_MC_DAMAGE = 5;
    private static final float GAUNTLET_RANGE = 1.5f;
    private static final float GAUNTLET_HITSCAN_STEP = 0.15f;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public Gauntlet() {
        super(QuakePlayer.WeaponSlot.GAUNTLET, GAUNTLET_REFIRE_RATE, false, null, true,
                GAUNTLET_QUAKE_DAMAGE, GAUNTLET_MC_DAMAGE, DamageSources.GAUNTLET_DAMAGE,
                GAUNTLET_RANGE, GAUNTLET_HITSCAN_STEP);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> {
            state.getController().setAnimation(DefaultAnimations.IDLE);
            return PlayState.CONTINUE;
        }).triggerableAnim("fire", RawAnimation.begin().thenLoop("firing"))
                .triggerableAnim("idle", DefaultAnimations.IDLE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            triggerAnim(user, GeoItem.getOrAssignId(user.getActiveItem(), (ServerWorld) world), "controller", "fire");
        }
        return super.use(world, user, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient) {
            triggerAnim(user, GeoItem.getOrAssignId(user.getActiveItem(), (ServerWorld) world), "controller", "idle");
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GauntletRenderer renderer = new GauntletRenderer();

            @Override
            public GauntletRenderer getCustomRenderer() {
                return this.renderer;
            }
        });

    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }
}
