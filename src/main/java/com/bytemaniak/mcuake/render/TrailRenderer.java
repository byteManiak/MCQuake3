package com.bytemaniak.mcuake.render;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
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
public class TrailRenderer implements WorldRenderEvents.End {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/misc/white.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityTranslucentEmissive(TEXTURE);

    private final static long RAILGUN_TRAIL_LIFETIME = 20;
    private final static long LIGHTNING_GUN_TRAIL_LIFETIME = 2;

    private final static Vec3d RAILGUN_TRAIL_COLOR = new Vec3d(.35f, 1, 0);
    private final static Vec3d LIGHTNING_GUN_TRAIL_COLOR = new Vec3d(.5, .85, 1);

    private class TrailData {
        public Vec3d v1, v2, v3, v4, v5, v6, v7, v8;
        public long startTick;
        public long lifetime;
        public Vec3d color;

        public TrailData(Vec3d v1, Vec3d v2, long startTick, long lifetime, Vec3d color) {
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
            this.lifetime = lifetime;
            this.color = color;
        }
    }

    private final CopyOnWriteArrayList<TrailData> trailList = new CopyOnWriteArrayList<TrailData>();

    private void genVertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, Vec3d vec, Vec3d col, float alpha) {
        float x = (float)vec.x;
        float y = (float)vec.y;
        float z = (float)vec.z;
        vertexConsumer.vertex(positionMatrix, x, y, z).color((float)col.x, (float)col.y, (float)col.z, 1-alpha).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(1).normal(normalMatrix, 0, 1, 0).next();
    }

    private void genQuad(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, Vec3d color, float alpha) {
        genVertex(vertexConsumer, positionMatrix, normalMatrix, v1, color, alpha);
        genVertex(vertexConsumer, positionMatrix, normalMatrix, v2, color, alpha);
        genVertex(vertexConsumer, positionMatrix, normalMatrix, v3, color, alpha);
        genVertex(vertexConsumer, positionMatrix, normalMatrix, v4, color, alpha);
    }

    @Override
    public void onEnd(WorldRenderContext context) {
        long worldTime = context.world().getTime();
        trailList.removeIf(trail -> (worldTime - trail.startTick > trail.lifetime));

        Vec3d cameraPos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();

        MatrixStack matrices = context.matrixStack();
        matrices.push();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix3f normalMatrix = matrices.peek().getNormalMatrix();
        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        VertexConsumerProvider.Immediate vertexConsumers = VertexConsumerProvider.immediate(new BufferBuilder(24));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(LAYER);

        for (Iterator<TrailData> iter = trailList.iterator(); iter.hasNext(); ) {
            TrailData trail = iter.next();

            long currentTick = worldTime - trail.startTick;
            float alpha = (float)currentTick / trail.lifetime;

            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v1, trail.v2, trail.v3, trail.v4, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v5, trail.v6, trail.v7, trail.v8, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v1, trail.v2, trail.v6, trail.v5, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v2, trail.v3, trail.v7, trail.v6, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v3, trail.v4, trail.v8, trail.v7, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v4, trail.v1, trail.v5, trail.v8, trail.color, alpha);
        }

        matrices.pop();
        if (MinecraftClient.getInstance().cameraEntity.isPlayer())
            vertexConsumers.draw();
    }

    public void addTrail(Vec3d v1, Vec3d v2, int type) {
        if (type == MCuakePlayer.WeaponSlot.RAILGUN.slot()) {
            addRailgunTrail(v1, v2);
        } else {
            addLightningGunTrail(v1, v2);
        }
    }

    private void addRailgunTrail(Vec3d v1, Vec3d v2) {
        trailList.add(new TrailData(v1, v2, MinecraftClient.getInstance().world.getTime(), RAILGUN_TRAIL_LIFETIME, RAILGUN_TRAIL_COLOR));
    }

    private void addLightningGunTrail(Vec3d v1, Vec3d v2) {
        trailList.add(new TrailData(v1, v2, MinecraftClient.getInstance().world.getTime(), LIGHTNING_GUN_TRAIL_LIFETIME, LIGHTNING_GUN_TRAIL_COLOR));
    }
}
