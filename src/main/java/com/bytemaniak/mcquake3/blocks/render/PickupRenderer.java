package com.bytemaniak.mcquake3.blocks.render;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.blocks.ammo.AmmoBoxEntity;
import com.bytemaniak.mcquake3.blocks.health.HealthEntity;
import com.bytemaniak.mcquake3.blocks.powerup.PowerupEntity;
import com.bytemaniak.mcquake3.blocks.shield.EnergyShieldEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PickupRenderer<T extends PickupEntity> extends GeoBlockRenderer<T> {
    public PickupRenderer(Identifier id) {
        super(new PickupModel<>(id));
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, T animatable, BakedGeoModel model, @Nullable RenderLayer renderType, VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (animatable instanceof AmmoBoxEntity || animatable instanceof HealthEntity ||
                animatable instanceof PowerupEntity || animatable instanceof EnergyShieldEntity) {
            poseStack.scale(1.5f, 1.5f, 1.5f);
            poseStack.translate(-0.15f, -0.025f, -0.15f);
        }
        ///if (!animatable.lastShouldRender) alpha = 0.105f;
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }
}
