package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.registry.Items;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class FullArsenalC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        player.giveItemStack(new ItemStack(Items.GAUNTLET));
        player.giveItemStack(new ItemStack(Items.MACHINEGUN));
        player.giveItemStack(new ItemStack(Items.SHOTGUN));
        player.giveItemStack(new ItemStack(Items.GRENADE_LAUNCHER));
        player.giveItemStack(new ItemStack(Items.ROCKET_LAUNCHER));
        player.giveItemStack(new ItemStack(Items.LIGHTNING_GUN));
        player.giveItemStack(new ItemStack(Items.RAILGUN));
        player.giveItemStack(new ItemStack(Items.PLASMAGUN));
        player.giveItemStack(new ItemStack(Items.BFG10K));
    }
}
