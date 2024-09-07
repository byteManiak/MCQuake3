package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.PortalEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class Portal extends Item implements GeoItem {
    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public Portal() { super(new Item.Settings()); }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if (!world.isClient) {
            ItemStack itemStack = context.getStack();
            Direction direction = context.getSide();
            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);
            PlayerEntity player = context.getPlayer();

            PortalEntity entity = Blocks.PORTAL_ENTITY.spawnFromItemStack((ServerWorld) world, itemStack, player, blockPos2, SpawnReason.SPAWN_EGG, true, false);
            if (entity != null) {
                itemStack.decrement(1);
                entity.setFacing(context.getHorizontalPlayerFacing().rotateYClockwise());
            }
            return ActionResult.CONSUME;
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Link to a destination position"));
        tooltip.add(Text.of("by using the [MCQuake3 Tool]."));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private final GeoItemRenderer<?> renderer =
                    new GeoItemRenderer<>(new DefaultedItemGeoModel<>(Identifier.of("mcquake3:portal")));

            @Override
            public @Nullable BuiltinModelItemRenderer getGeoItemRenderer() { return renderer; }
        });
    }

    @Override
    public Object getRenderProvider() { return cache.getRenderProvider(); }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }
}
