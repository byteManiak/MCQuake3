package com.bytemaniak.mcuake.cs.packets.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class ShowRailgunTrailS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Vec3d playerPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3d endPos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        double distance = endPos.distanceTo(playerPos);
        Vec3d difference = endPos.subtract(playerPos).multiply(1.f / distance);
        for (int i = 0; i < distance; i++) {
            Vec3d particlePos = playerPos.add(difference.multiply(i));
            MinecraftClient.getInstance().world.addParticle(ParticleTypes.ANGRY_VILLAGER, particlePos.x, particlePos.y, particlePos.z, 1, 1, 1);
        }
    }
}
