package com.bytemaniak.mcquake3.blocks.render;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class ArmorFixRenderer<T extends PickupEntity> extends PickupRenderer<T>{
    public ArmorFixRenderer(Identifier id) { super(id); }

    @Override
    public void actuallyRender(MatrixStack poseStack, T animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (!animatable.lastShouldRender) alpha = 0.105f;
        poseStack.scale(1.33f, 1.33f, 1.33f);
        poseStack.translate(-0.133f, -0.025f, -0.133f);
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
