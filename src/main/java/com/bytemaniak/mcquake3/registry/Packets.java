package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.network.c2s.*;
import com.bytemaniak.mcquake3.network.s2c.DealtDamageS2CPacket;
import com.bytemaniak.mcquake3.network.s2c.JumppadPowerS2CPacket;
import com.bytemaniak.mcquake3.network.s2c.PickupVisibilityS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class Packets {
    public static final Identifier DEALT_DAMAGE = new Identifier("mcquake3", "dealt_damage");
    public static final Identifier JUMPPAD_UPDATE_POWER = new Identifier("mcquake3", "jumppad_update_power");
    public static final Identifier JUMPPAD_UPDATED_POWER = new Identifier("mcquake3", "jumppad_updated_power");
    public static final Identifier QUAKE_GUI_UPDATE = new Identifier("mcquake3", "quake_gui_update");
    public static final Identifier QUAKE_PLAYER_SOUNDS_UPDATE = new Identifier("mcquake3", "quake_player_sounds_update");
    public static final Identifier PLAYER_CLASS_UPDATE = new Identifier("mcquake3", "player_class_update");
    public static final Identifier FULL_ARSENAL_REQUEST = new Identifier("mcquake3", "full_arsenal_request");
    public static final Identifier AMMO_BOX_UPDATE = new Identifier("mcquake3", "ammo_box_update");

    public static void registerClientPackets()
    {
        ClientPlayNetworking.registerGlobalReceiver(Packets.JUMPPAD_UPDATED_POWER, JumppadPowerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(Packets.DEALT_DAMAGE, DealtDamageS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(Packets.AMMO_BOX_UPDATE, PickupVisibilityS2CPacket::receive);
    }

    public static void registerServerPackets()
    {
        ServerPlayNetworking.registerGlobalReceiver(Packets.JUMPPAD_UPDATE_POWER, JumppadPowerC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(Packets.QUAKE_GUI_UPDATE, QuakeGuiUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(Packets.QUAKE_PLAYER_SOUNDS_UPDATE, QuakePlayerSoundsUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(Packets.PLAYER_CLASS_UPDATE, PlayerClassUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(Packets.FULL_ARSENAL_REQUEST, FullArsenalC2SPacket::receive);
    }
}
