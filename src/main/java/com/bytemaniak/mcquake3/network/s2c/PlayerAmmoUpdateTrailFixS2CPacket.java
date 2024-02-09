package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public class PlayerAmmoUpdateTrailFixS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        UUID playerUuid = buf.readUuid();
        boolean hasLightningAmmo = buf.readBoolean();
        boolean hasRailgunAmmo = buf.readBoolean();
        PlayerEntity player = client.world.getPlayerByUuid(playerUuid);
        if (player != null) {
            ItemStack lightningAmmo = hasLightningAmmo ? new ItemStack(Weapons.LIGHTNING_CELL) : ItemStack.EMPTY;
            ItemStack railgunAmmo = hasRailgunAmmo ? new ItemStack(Weapons.RAILGUN_ROUND) : ItemStack.EMPTY;
            player.getInventory().setStack(2, lightningAmmo);
            player.getInventory().setStack(3, railgunAmmo);
        }
    }
}
