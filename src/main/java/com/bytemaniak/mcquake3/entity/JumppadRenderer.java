package com.bytemaniak.mcquake3.entity;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class JumppadRenderer extends GeoEntityRenderer<JumppadEntity> {
    public JumppadRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DefaultedEntityGeoModel<>(new Identifier("mcquake3:jumppad"), true));
    }

    @Override
    public void preRender(MatrixStack poseStack, JumppadEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Direction facing = animatable.getFacing();
        if (facing != Direction.UP)
            poseStack.translate(0, 0.6, 0);

        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90-animatable.getYaw()));
        poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(animatable.getPitch()));
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
