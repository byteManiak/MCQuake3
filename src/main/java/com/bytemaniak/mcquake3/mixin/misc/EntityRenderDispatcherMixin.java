package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.interfaces.MultiCollidable;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;renderHitbox(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/entity/Entity;F)V"))
    private void renderJumppadColliders(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, Operation<Void> original) {
        if (entity instanceof MultiCollidable multiCollidable)
            for (VoxelShape shape : multiCollidable.getColliders())
                WorldRenderer.drawBox(matrices, vertices, shape.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ()), 0f, 1f, 0f, 1f);
        original.call(matrices, vertices, entity, tickDelta);
    }
}