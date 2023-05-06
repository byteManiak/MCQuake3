package com.bytemaniak.mcuake.network.s2c;

import com.bytemaniak.mcuake.MCuakeClient;
import com.bytemaniak.mcuake.entity.QuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class ShowTrailS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Vec3d playerPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3d endPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        UUID playerId = buf.readUuid();
        int type = buf.readInt();

        MCuakeClient.trailRenderer.addTrail(playerPos, endPos, playerId, type);

        if (type == QuakePlayer.WeaponSlot.RAILGUN.slot()) {
            double distance = endPos.distanceTo(playerPos);
            Vec3d difference = endPos.subtract(playerPos).multiply(1.f / distance);
            for (int i = 0; i < distance; i++) {
                Vec3d particlePos = playerPos.add(difference.multiply(i));
                MinecraftClient.getInstance().world.addParticle(ParticleTypes.POOF, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
            }
        }
    }
}
