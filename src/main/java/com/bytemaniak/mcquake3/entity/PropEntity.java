package com.bytemaniak.mcquake3.entity;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class PropEntity extends Entity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final Item droppedItem;

    public PropEntity(EntityType<?> type, World world, Item droppedItem) {
        super(type, world);
        this.droppedItem = droppedItem;
    }

    @Override
    public ItemStack getPickBlockStack() { return new ItemStack(droppedItem); }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.getSource() instanceof PlayerEntity player) {
            if (source.isSourceCreativePlayer() && (player.getActiveItem() == ItemStack.EMPTY || player.getActiveItem().isOf(Weapons.TOOL)))
                return false;

            return !player.getActiveItem().isOf(Weapons.TOOL);
        }

        return true;
    }


    @Override
    public boolean canHit() { return !isRemoved(); }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) return false;

        ItemStack itemStack = new ItemStack(droppedItem);
        if (hasCustomName()) itemStack.setCustomName(this.getCustomName());
        dropStack(itemStack);
        kill();
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }
}
