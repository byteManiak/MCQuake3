package com.bytemaniak.mcuake.registry;

import com.bytemaniak.mcuake.network.c2s.JumppadPowerC2SPacket;
import com.bytemaniak.mcuake.network.c2s.PlayerClassUpdateC2SPacket;
import com.bytemaniak.mcuake.network.c2s.QuakeModeUpdateC2SPacket;
import com.bytemaniak.mcuake.network.s2c.DealtDamageS2CPacket;
import com.bytemaniak.mcuake.network.s2c.JumppadPowerS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class Packets {
    public static final Identifier DEALT_DAMAGE = new Identifier("mcuake", "dealt_damage");
    public static final Identifier JUMPPAD_UPDATE_POWER = new Identifier("mcuake", "jumppad_update_power");
    public static final Identifier JUMPPAD_UPDATED_POWER = new Identifier("mcuake", "jumppad_updated_power");
    public static final Identifier SHOW_TRAIL = new Identifier("mcuake", "show_trail");
    public static final Identifier QUAKE_MODE_UPDATE = new Identifier("mcuake", "quake_mode_update");
    public static final Identifier PLAYER_CLASS_UPDATE = new Identifier("mcuake", "player_class_update");

    public static void registerClientPackets()
    {
        ClientPlayNetworking.registerGlobalReceiver(Packets.DEALT_DAMAGE, DealtDamageS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(Packets.JUMPPAD_UPDATED_POWER, JumppadPowerS2CPacket::receive);
    }

    public static void registerServerPackets()
    {
        ServerPlayNetworking.registerGlobalReceiver(Packets.JUMPPAD_UPDATE_POWER, JumppadPowerC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(Packets.QUAKE_MODE_UPDATE, QuakeModeUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(Packets.PLAYER_CLASS_UPDATE, PlayerClassUpdateC2SPacket::receive);
    }
}
