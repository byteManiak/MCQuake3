package com.bytemaniak.mcquake3.entity.projectile.render;

import com.bytemaniak.mcquake3.entity.projectile.Grenade;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GrenadeRenderer extends GeoEntityRenderer<Grenade> {

    public GrenadeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DefaultedEntityGeoModel<>(new Identifier("mcquake3", "grenade"), true));
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, Grenade animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float time = (float)animatable.world.getTime();
        float timeDeg = time * 18;

        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(timeDeg));
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
