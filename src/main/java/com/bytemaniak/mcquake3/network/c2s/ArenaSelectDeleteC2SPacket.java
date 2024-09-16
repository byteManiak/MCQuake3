package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import com.bytemaniak.mcquake3.registry.Packets;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public record ArenaSelectDeleteC2SPacket(String arenaName, boolean delete, boolean startEditing) implements CustomPayload {
    public static final CustomPayload.Id<ArenaSelectDeleteC2SPacket> ID = new Id<>(Packets.ARENA_SELECT_DELETE);
    public static final PacketCodec<ByteBuf, ArenaSelectDeleteC2SPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, ArenaSelectDeleteC2SPacket::arenaName,
            PacketCodecs.BOOL, ArenaSelectDeleteC2SPacket::delete,
            PacketCodecs.BOOL, ArenaSelectDeleteC2SPacket::startEditing,
            ArenaSelectDeleteC2SPacket::new
    );

    public static void receive(ArenaSelectDeleteC2SPacket payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            String arenaName = payload.arenaName;
            boolean delete = payload.delete;
            QuakeArenasParameters state = QuakeArenasParameters.getServerState(context.server());

            if (delete) {
                state.deleteArena(arenaName);
                player.sendMessage(Text.of("Deleted arena " + arenaName), true);
            } else {
                boolean startEditing = payload.startEditing;
                state.createInitialArenaData(arenaName);
                if (startEditing) {
                    ((QuakePlayer) player).mcquake3$setCurrentlyEditingArena(arenaName);
                    player.sendMessage(Text.of("Selected arena " + arenaName + " to add spawnpoints to"), true);
                }
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
