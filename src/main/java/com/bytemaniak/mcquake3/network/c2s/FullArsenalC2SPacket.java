package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FullArsenalC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (player.hasPermissionLevel(2)) {
            player.giveItemStack(new ItemStack(Weapons.GAUNTLET));
            player.giveItemStack(new ItemStack(Weapons.MACHINEGUN));
            player.giveItemStack(new ItemStack(Weapons.SHOTGUN));
            player.giveItemStack(new ItemStack(Weapons.GRENADE_LAUNCHER));
            player.giveItemStack(new ItemStack(Weapons.ROCKET_LAUNCHER));
            player.giveItemStack(new ItemStack(Weapons.LIGHTNING_GUN));
            player.giveItemStack(new ItemStack(Weapons.RAILGUN));
            player.giveItemStack(new ItemStack(Weapons.PLASMAGUN));
            player.giveItemStack(new ItemStack(Weapons.BFG10K));
        } else player.sendMessageToClient(Text.of("MCQuake3: You do not have access to that command."), false);
    }
}
