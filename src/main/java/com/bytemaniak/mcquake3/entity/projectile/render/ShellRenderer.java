package com.bytemaniak.mcquake3.entity.projectile.render;

import com.bytemaniak.mcquake3.entity.projectile.Shell;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class ShellRenderer extends EntityRenderer<Shell> {
    private static final Identifier TEXTURE = new Identifier("mcquake3:textures/entity/shell.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityTranslucentEmissive(TEXTURE);

    public ShellRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(Shell entity) {
        return TEXTURE;
    }

    @Override
    public void render(Shell entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Copied from DragonFireballEntityRenderer.render()
        matrices.push();
        matrices.scale(.125f, .125f, .125f);
        matrices.multiply(dispatcher.getRotation());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(LAYER);
        produceVertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 0, 0, 1);
        produceVertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 0, 1, 1);
        produceVertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 1, 1, 0);
        produceVertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 1, 0, 0);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private static void produceVertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, int light, float x, float y, int textureU, int textureV) {
        vertexConsumer.vertex(positionMatrix, x - 0.5F, y - 0.25F, 0.0F).color(255, 255, 255, 255).texture((float)textureU, (float)textureV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }
}
