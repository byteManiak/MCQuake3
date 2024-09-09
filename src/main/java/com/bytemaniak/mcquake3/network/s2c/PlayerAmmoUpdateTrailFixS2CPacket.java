package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Weapons;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record PlayerAmmoUpdateTrailFixS2CPacket(UUID playerUuid, boolean hasLightningAmmo, boolean hasRailgunAmmo) implements CustomPayload {
    public static final CustomPayload.Id<PlayerAmmoUpdateTrailFixS2CPacket> ID = new CustomPayload.Id<>(Packets.PLAYER_AMMO_TRAIL_FIX);
    public static final PacketCodec<ByteBuf, PlayerAmmoUpdateTrailFixS2CPacket> CODEC = PacketCodec.tuple(
            Uuids.PACKET_CODEC, PlayerAmmoUpdateTrailFixS2CPacket::playerUuid,
            PacketCodecs.BOOL, PlayerAmmoUpdateTrailFixS2CPacket::hasLightningAmmo,
            PacketCodecs.BOOL, PlayerAmmoUpdateTrailFixS2CPacket::hasRailgunAmmo,
            PlayerAmmoUpdateTrailFixS2CPacket::new
    );

    public static void receive(PlayerAmmoUpdateTrailFixS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            UUID playerUuid = payload.playerUuid;
            boolean hasLightningAmmo = payload.hasLightningAmmo;
            boolean hasRailgunAmmo = payload.hasRailgunAmmo;
            PlayerEntity player = context.client().world.getPlayerByUuid(playerUuid);
            if (player != null) {
                ItemStack lightningAmmo = hasLightningAmmo ? new ItemStack(Weapons.LIGHTNING_CELL) : ItemStack.EMPTY;
                ItemStack railgunAmmo = hasRailgunAmmo ? new ItemStack(Weapons.RAILGUN_ROUND) : ItemStack.EMPTY;
                PlayerInventory inventory = player.getInventory();
                int hotbarOffset = PlayerInventory.getHotbarSize();

                inventory.setStack(hotbarOffset, lightningAmmo);
                inventory.setStack(hotbarOffset + 1, railgunAmmo);
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
