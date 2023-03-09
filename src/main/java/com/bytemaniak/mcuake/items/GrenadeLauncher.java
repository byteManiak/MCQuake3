package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.items.client.GrenadeLauncherRenderer;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
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

public class GrenadeLauncher extends Weapon implements GeoItem {
    private static final long GRENADE_REFIRE_RATE = 10;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public GrenadeLauncher() {
        super(QuakePlayer.WeaponSlot.GRENADE_LAUNCHER, GRENADE_REFIRE_RATE, true, Sounds.PLASMAGUN_FIRE, false);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> {
            state.getController().setAnimation(DefaultAnimations.IDLE);
            return PlayState.CONTINUE;
        }).triggerableAnim("shoot", RawAnimation.begin().thenPlay("shoot"))
                .triggerableAnim("idle", DefaultAnimations.IDLE));
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GrenadeLauncherRenderer renderer = new GrenadeLauncherRenderer();

            @Override
            public GrenadeLauncherRenderer getCustomRenderer() {
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
