package com.bytemaniak.mcquake3.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class PortalRenderer extends DynamicGeoEntityRenderer<PortalEntity> {
    public PortalRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DefaultedEntityGeoModel<>(new Identifier("mcquake3:portal"), true));
    }

    @Override
    public RenderLayer getRenderType(PortalEntity animatable, Identifier texture, VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucentCull(texture);
    }

    @Override
    protected RenderLayer getRenderTypeOverrideForBone(GeoBone bone, PortalEntity animatable, Identifier texturePath, VertexConsumerProvider bufferSource, float partialTick) {
        if (bone.getName().equals("bb_main"))
            return RenderLayer.getEndPortal();

        return super.getRenderTypeOverrideForBone(bone, animatable, texturePath, bufferSource, partialTick);
    }

    @Override
    public void preRender(MatrixStack poseStack, PortalEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90-animatable.getYaw()));
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
