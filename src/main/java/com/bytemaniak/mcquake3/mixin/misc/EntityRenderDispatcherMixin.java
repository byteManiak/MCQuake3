package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.util.MultiCollidable;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Inject(method = "renderHitbox", at = @At("HEAD"))
    private static void renderJumppadColliders(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, float red, float green, float blue, CallbackInfo ci) {
        if (entity instanceof MultiCollidable multiCollidable)
            for (VoxelShape shape : multiCollidable.getColliders())
                WorldRenderer.drawBox(matrices, vertices, shape.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ()), 0f, 1f, 0f, 1f);
    }
}