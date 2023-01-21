package com.bytemaniak.mcuake.cs;

import com.bytemaniak.mcuake.cs.packets.c2s.JumppadPowerC2SPacket;
import com.bytemaniak.mcuake.cs.packets.s2c.JumppadPowerS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class CSMessages {
    public static final Identifier JUMPPAD_UPDATE_POWER = new Identifier("mcuake", "jumppad_update_power");
    public static final Identifier JUMPPAD_UPDATED_POWER = new Identifier("mcuake", "jumppad_updated_power");

    public static void registerClientPackets()
    {
        ClientPlayNetworking.registerGlobalReceiver(CSMessages.JUMPPAD_UPDATED_POWER, JumppadPowerS2CPacket::receive);
    }

    public static void registerServerPackets()
    {
        ServerPlayNetworking.registerGlobalReceiver(CSMessages.JUMPPAD_UPDATE_POWER, JumppadPowerC2SPacket::receive);
    }
}
