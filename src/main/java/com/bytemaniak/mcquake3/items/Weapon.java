package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.network.SerializableDataTicket;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Weapon extends Item implements GeoItem, FabricItem {
    private static final float HITSCAN_HORIZONTAL_OFFSET = .2f;

    protected static final float PROJECTILE_VERTICAL_SPAWN_OFFSET = .6f;
    protected static final float PROJECTILE_HORIZONTAL_SPAWN_OFFSET = -.1f;
    protected static final float PROJECTILE_FORWARD_SPAWN_OFFSET = .5f;
    protected static final float PROJECTILE_DIRECTION_RANGE = 200;

    private final Identifier weaponIdentifier;
    private final long refireRateQ3;
    private final long refireRateQL;
    private final boolean hasRepeatedFiringSound;
    private final SoundEvent firingSound;
    public final boolean hasActiveLoopSound;

    public final Item ammoType;
    public final int startingAmmo;
    public final int ammoBoxCount;
    public final int slot;

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public static final SerializableDataTicket<Double> SPEED = SerializableDataTicket.ofDouble(new Identifier("mcquake3:firing_speed"));

    protected Weapon(Identifier id, long refireRateQ3InTicks, long refireRateQLInTicks,
                     boolean hasRepeatedFiringSound, SoundEvent firingSound, boolean hasActiveLoopSound,
                     Item ammoType, int startingAmmo, int ammoBoxCount, int slot) {
        super(new Item.Settings().maxCount(1));
        this.ammoType = ammoType;
        this.startingAmmo = startingAmmo;
        this.ammoBoxCount = ammoBoxCount;
        this.slot = slot;

        this.weaponIdentifier = id;

        this.refireRateQ3 = refireRateQ3InTicks;
        this.refireRateQL = refireRateQLInTicks;
        this.hasRepeatedFiringSound = hasRepeatedFiringSound;
        this.firingSound = firingSound;
        this.hasActiveLoopSound = hasActiveLoopSound;

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // By calling this function, the weapon continues to stay used until the player stops pressing the use key
        user.setCurrentHand(hand);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) { return UseAction.BOW; }

    @Override
    // Player can shoot weapon indefinitely
    public int getMaxUseTime(ItemStack stack) { return 1000000; }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!user.isAlive()) return;

        long currentTick = world.getTime();
        QuakePlayer player = (QuakePlayer) user;
        long refireRate = player.hasQLRefireRate() ? refireRateQL : refireRateQ3;

        StatusEffectInstance playerHaste = user.getStatusEffect(StatusEffects.HASTE);
        float refireModifier = 1;

        if (playerHaste != null)
            refireModifier = playerHaste.getAmplifier() > 3 ? 0.7f : (1-playerHaste.getAmplifier()*0.1f);

        if (currentTick - player.getWeaponTick(slot) >= (long)((float)refireRate * refireModifier)) {
            PlayerEntity p = (PlayerEntity) user;
            boolean hasAmmo = false;

            if (p.isCreative() || ammoType == null) hasAmmo = true;
            else for (int i = 0; i < p.getInventory().size(); ++i) {
                ItemStack itemStack = p.getInventory().getStack(i);
                if (itemStack.isOf(ammoType)) {
                    hasAmmo = true;
                    itemStack.decrement(1);
                    if (itemStack.isEmpty()) p.getInventory().removeOne(itemStack);
                    break;
                }
            }

            if (hasAmmo) {
                // Whatever projectile the weapon shoots, its initial position is approximated
                // to be shot from the held weapon, not from the player's eye
                Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw());
                Vec3d upDir = Vec3d.fromPolar(user.getPitch() + 90, user.getYaw());
                Vec3d rightDir = lookDir.crossProduct(upDir).normalize().multiply(HITSCAN_HORIZONTAL_OFFSET);
                Vec3d weaponPos = user.getEyePos().subtract(rightDir);
                onWeaponRefire(world, user, stack, lookDir, weaponPos);

                if (hasRepeatedFiringSound)
                    world.playSoundFromEntity(null, user, firingSound, SoundCategory.PLAYERS, 1, 1);
            } else if (world.isClient)
                // Scroll to the next available slot in the hotbar in case
                // the currently held weapon has run out of ammo
                player.scrollToNextSuitableSlot();

            player.setWeaponTick(slot, currentTick);
        }
    }

    protected abstract void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public Supplier<Object> getRenderProvider() { return renderProvider; }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GeoItemRenderer<?> renderer =
                    new GeoItemRenderer<>(new DefaultedItemGeoModel<>(weaponIdentifier));

            @Override
            public GeoItemRenderer<?> getCustomRenderer() {
                return renderer;
            }
        });
    }

    protected PlayState handle(AnimationState<Weapon> state) { return null; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", this::handle)
                .triggerableAnim("shoot", DefaultAnimations.ATTACK_SHOOT)
                .triggerableAnim("idle", DefaultAnimations.IDLE));
    }
}
