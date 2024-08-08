package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.PortalEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Portal extends Item implements GeoItem {
    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public Portal() { super(new FabricItemSettings()); }

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
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GeoItemRenderer<?> renderer =
                    new GeoItemRenderer<>(new DefaultedItemGeoModel<>(new Identifier("mcquake3:portal")));

            @Override
            public GeoItemRenderer<?> getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() { return renderProvider; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }
}
