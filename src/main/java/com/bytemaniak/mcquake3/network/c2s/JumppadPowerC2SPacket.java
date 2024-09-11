package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import com.bytemaniak.mcquake3.network.s2c.JumppadPowerS2CPacket;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.screen.JumppadScreenHandler;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public record JumppadPowerC2SPacket(byte power) implements CustomPayload {
    public static final CustomPayload.Id<JumppadPowerC2SPacket> ID = new Id<>(Packets.JUMPPAD_UPDATE_POWER);
    public static final PacketCodec<ByteBuf, JumppadPowerC2SPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE, JumppadPowerC2SPacket::power,
            JumppadPowerC2SPacket::new
    );

    public static void receive(JumppadPowerC2SPacket payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            if (player.currentScreenHandler instanceof JumppadScreenHandler) {
                // Update the jump pad stats with the ones received from the GUI user
                // and broadcast back to all players
                ServerWorld world = player.getServerWorld();
                Entity entity = world.getEntityById(((JumppadScreenHandler) player.currentScreenHandler).id);
                if (entity instanceof JumppadEntity jumppad) {
                    jumppad.updatePower(payload.power);
                    JumppadPowerS2CPacket retBuf = new JumppadPowerS2CPacket(jumppad.getId(), jumppad.getPower());
                    for (ServerPlayerEntity plr : PlayerLookup.world(context.player().getServerWorld()))
                        ServerPlayNetworking.send(plr, retBuf);
                }
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
