package com.bytemaniak.mcquake3.render;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

// Code for the quad damage effect was adapted from the EnergySwirlOverlayFeatureRenderer class
public class QuadDamageGlintRenderer<T extends AbstractClientPlayerEntity, M extends PlayerEntityModel<T>>
        extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public QuadDamageGlintRenderer(
            FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext,
            EntityModelLoader loader) {
        super(featureRendererContext);
        this.model = new PlayerEntityModel<>(loader.getModelPart(EntityModelLayers.PLAYER), false);
    }

    private static final Identifier OVERLAY = new Identifier("textures/entity/creeper/creeper_armor.png");
    private final PlayerEntityModel<AbstractClientPlayerEntity> model;

    protected float getEnergySwirlX(float partialAge) {
        return partialAge * 0.01f;
    }

    protected EntityModel<AbstractClientPlayerEntity> getEnergySwirlModel() {
        return this.model;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!((QuakePlayer)entity).hasQuadDamage()) return;

        float f = (float)entity.age + tickDelta;
        EntityModel<AbstractClientPlayerEntity> entityModel = this.getEnergySwirlModel();
        entityModel.animateModel(entity, limbAngle, limbDistance, tickDelta);
        this.getContextModel().copyStateTo(entityModel);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(OVERLAY, this.getEnergySwirlX(f) % 1.0f, f * 0.01f % 1.0f));
        entityModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0.5f, 0.5f, 0.5f, 1.0f);
    }
}
