package com.bytemaniak.mcuake.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

@Environment(EnvType.CLIENT)
public class RailRenderer implements WorldRenderEvents.AfterTranslucent {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/misc/white.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityTranslucentEmissive(TEXTURE);

    private final static long RAILGUN_TRAIL_LIFETIME = 20;

    private class TrailData {
        public Vec3d v1, v2, v3, v4, v5, v6, v7, v8;
        public long startTick;

        public TrailData(Vec3d v1, Vec3d v2, long startTick) {
            Vec3d diffVec = v2.subtract(v1);
            Vec3d leftVec = diffVec.normalize().crossProduct(new Vec3d(0, 1, 0)).multiply(.05f);
            Vec3d upVec = leftVec.crossProduct(diffVec.normalize());
            this.v1 = v1.add(leftVec).add(upVec);
            this.v2 = v1.add(leftVec).add(upVec.negate());;
            this.v3 = v1.add(leftVec.negate()).add(upVec.negate());
            this.v4 = v1.add(leftVec.negate()).add(upVec);;
            this.v5 = this.v1.add(diffVec);
            this.v6 = this.v2.add(diffVec);
            this.v7 = this.v3.add(diffVec);
            this.v8 = this.v4.add(diffVec);
            this.startTick = startTick;
        }
    }

    private final CopyOnWriteArrayList<TrailData> trailList = new CopyOnWriteArrayList<TrailData>();

    private static void produceVertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, float z, float alpha) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(.35f, 1, 0, 1-alpha).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(1).normal(normalMatrix, 0, 1, 0).next();
    }

    private void genVertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, Vec3d vec, float alpha) {
        produceVertex(vertexConsumer, positionMatrix, normalMatrix, (float)vec.x, (float)vec.y, (float)vec.z, alpha);
    }

    private void genQuad(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, float alpha) {
        genVertex(vertexConsumer, positionMatrix, normalMatrix, v1, alpha);
        genVertex(vertexConsumer, positionMatrix, normalMatrix, v2, alpha);
        genVertex(vertexConsumer, positionMatrix, normalMatrix, v3, alpha);
        genVertex(vertexConsumer, positionMatrix, normalMatrix, v4, alpha);
    }

    @Override
    public void afterTranslucent(WorldRenderContext context) {
        long worldTime = context.world().getTime();
        trailList.removeIf(trail -> (worldTime - trail.startTick > RAILGUN_TRAIL_LIFETIME));

        Vec3d cameraPos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();

        MatrixStack matrices = context.matrixStack();
        matrices.push();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix3f normalMatrix = matrices.peek().getNormalMatrix();
        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        VertexConsumerProvider.Immediate vertexConsumers = VertexConsumerProvider.immediate(new BufferBuilder(8));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(LAYER);

        for (Iterator<TrailData> iter = trailList.iterator(); iter.hasNext(); ) {
            TrailData trail = iter.next();

            long currentTick = worldTime - trail.startTick;
            float alpha = (float)currentTick / RAILGUN_TRAIL_LIFETIME;

            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v1, trail.v2, trail.v3, trail.v4, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v5, trail.v6, trail.v7, trail.v8, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v1, trail.v2, trail.v6, trail.v5, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v2, trail.v3, trail.v7, trail.v6, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v3, trail.v4, trail.v8, trail.v7, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v4, trail.v1, trail.v5, trail.v8, alpha);
        }

        matrices.pop();
        if (MinecraftClient.getInstance().cameraEntity.isPlayer())
            vertexConsumers.draw();
    }

    public void addRailgunTrail(Vec3d v1, Vec3d v2) {
        trailList.add(new TrailData(v1, v2, MinecraftClient.getInstance().world.getTime()));
    }
}
