package com.bytemaniak.mcquake3.entity.projectile.render;

import com.bytemaniak.mcquake3.entity.projectile.Rocket;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RocketRenderer extends GeoEntityRenderer<Rocket> {
    public RocketRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DefaultedEntityGeoModel<>(new Identifier("mcquake3", "rocket"), true));
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, Rocket animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Vec3d vel = animatable.getVelocity();
        double d = Math.sqrt(vel.x * vel.x + vel.z * vel.z);
        float pitch = (float)Math.toDegrees(Math.atan2(-vel.y, d));
        float yaw = (float)Math.toDegrees(Math.atan2(vel.x, vel.z));
        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));
        poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(pitch));
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
