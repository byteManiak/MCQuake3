package com.bytemaniak.mcquake3.render;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
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

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Environment(EnvType.CLIENT)
public class TrailRenderer implements WorldRenderEvents.End {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/misc/white.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityTranslucentEmissive(TEXTURE);
    private static final VertexConsumerProvider.Immediate vertexConsumerProvider = VertexConsumerProvider.immediate(new BufferBuilder(24));
    private final static long RAILGUN_TRAIL_LIFETIME = 20;
    private final static long LIGHTNING_GUN_TRAIL_LIFETIME = 3;

    private final static Vec3d RAILGUN_TRAIL_COLOR = new Vec3d(.35f, 1, 0);
    private final static Vec3d LIGHTNING_GUN_TRAIL_COLOR = new Vec3d(.5, .85, 1);

    private static class TrailData {
        public Vec3d v1, v2, v3, v4, v5, v6, v7, v8;
        public Vec3d _v1, _v2, _v3, _v4, _v5, _v6, _v7, _v8;
        public long startTick;
        public long lifetime;
        public Vec3d color;
        public UUID owner;

        public static void updateTrailData(TrailData trailData, Vec3d v1, Vec3d v2, long startTick) {
            Vec3d diffVec = v2.subtract(v1);
            Vec3d dirVec = diffVec.normalize();
            Vec3d leftVec = dirVec.crossProduct(new Vec3d(0, 1, 0)).multiply(.05f);
            Vec3d upVec = leftVec.crossProduct(diffVec.normalize());
            trailData._v1 = v1.add(leftVec).add(upVec);
            trailData._v2 = v1.add(leftVec).add(upVec.negate());
            trailData._v3 = v1.add(leftVec.negate()).add(upVec.negate());
            trailData._v4 = v1.add(leftVec.negate()).add(upVec);
            trailData._v5 = trailData._v1.add(diffVec);
            trailData._v6 = trailData._v2.add(diffVec);
            trailData._v7 = trailData._v3.add(diffVec);
            trailData._v8 = trailData._v4.add(diffVec);
            trailData.startTick = startTick;
        }

        public TrailData(Vec3d v1, Vec3d v2, long startTick, long lifetime, Vec3d color, UUID owner) {
            updateTrailData(this, v1, v2, startTick);
            this.v1 = this._v1;
            this.v2 = this._v2;
            this.v3 = this._v3;
            this.v4 = this._v4;
            this.v5 = this._v5;
            this.v6 = this._v6;
            this.v7 = this._v7;
            this.v8 = this._v8;
            this.lifetime = lifetime;
            this.color = color;
            this.owner = owner;
        }
    }

    private final CopyOnWriteArrayList<TrailData> trailList = new CopyOnWriteArrayList<>();

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
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);

        for (TrailData trail : trailList) {
            long currentTick = worldTime - trail.startTick;
            float alpha = (float) currentTick / trail.lifetime;

            trail.v1 = trail.v1.lerp(trail._v1, alpha);
            trail.v2 = trail.v2.lerp(trail._v2, alpha);
            trail.v3 = trail.v3.lerp(trail._v3, alpha);
            trail.v4 = trail.v4.lerp(trail._v4, alpha);
            trail.v5 = trail.v5.lerp(trail._v5, alpha);
            trail.v6 = trail.v6.lerp(trail._v6, alpha);
            trail.v7 = trail.v7.lerp(trail._v7, alpha);
            trail.v8 = trail.v8.lerp(trail._v8, alpha);

            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v1, trail.v2, trail.v3, trail.v4, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v5, trail.v6, trail.v7, trail.v8, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v1, trail.v2, trail.v6, trail.v5, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v2, trail.v3, trail.v7, trail.v6, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v3, trail.v4, trail.v8, trail.v7, trail.color, alpha);
            genQuad(vertexConsumer, positionMatrix, normalMatrix, trail.v4, trail.v1, trail.v5, trail.v8, trail.color, alpha);
        }

        matrices.pop();
        vertexConsumerProvider.draw();
    }

    public void addTrail(Vec3d v1, Vec3d v2, UUID playerId, int type) {
        if (type == QuakePlayer.WeaponSlot.RAILGUN.slot) {
            addRailgunTrail(v1, v2);
        } else {
            addLightningGunTrail(v1, v2, playerId);
        }
    }

    private void addRailgunTrail(Vec3d v1, Vec3d v2) {
        trailList.add(new TrailData(v1, v2, MinecraftClient.getInstance().world.getTime(), RAILGUN_TRAIL_LIFETIME, RAILGUN_TRAIL_COLOR, UUID.fromString("00000000-0000-0000-0000-000000000000")));
    }

    private void addLightningGunTrail(Vec3d v1, Vec3d v2, UUID playerId) {
        Optional<TrailData> trail = trailList.stream().filter(t -> t.owner.equals(playerId)).findFirst();
        if (trail.isEmpty()) {
            trailList.add(new TrailData(v1, v2, MinecraftClient.getInstance().world.getTime(), LIGHTNING_GUN_TRAIL_LIFETIME, LIGHTNING_GUN_TRAIL_COLOR, playerId));
        } else {
            TrailData trailData = trail.get();
            TrailData.updateTrailData(trailData, v1, v2, MinecraftClient.getInstance().world.getTime());
        }
    }
}
