package com.bytemaniak.mcuake.cs;

import com.bytemaniak.mcuake.cs.packets.c2s.DealtGauntletDamageC2SPacket;
import com.bytemaniak.mcuake.cs.packets.c2s.JumppadPowerC2SPacket;
import com.bytemaniak.mcuake.cs.packets.c2s.PlayerClassUpdateC2SPacket;
import com.bytemaniak.mcuake.cs.packets.c2s.QuakeModeUpdateC2SPacket;
import com.bytemaniak.mcuake.cs.packets.s2c.DealtDamageS2CPacket;
import com.bytemaniak.mcuake.cs.packets.s2c.JumppadPowerS2CPacket;
import com.bytemaniak.mcuake.cs.packets.s2c.ShowRailgunTrailS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class CSMessages {
    public static final Identifier DEALT_DAMAGE = new Identifier("mcuake", "dealt_damage");
    public static final Identifier GAUNTLET_DAMAGE = new Identifier("mcuake", "gauntlet_damage");
    public static final Identifier JUMPPAD_UPDATE_POWER = new Identifier("mcuake", "jumppad_update_power");
    public static final Identifier JUMPPAD_UPDATED_POWER = new Identifier("mcuake", "jumppad_updated_power");
    public static final Identifier SHOW_RAILGUN_TRAIL = new Identifier("mcuake", "show_railgun_trail");
    public static final Identifier QUAKE_MODE_UPDATE = new Identifier("mcuake", "quake_mode_update");
    public static final Identifier PLAYER_CLASS_UPDATE = new Identifier("mcuake", "player_class_update");

    public static void registerClientPackets()
    {
        ClientPlayNetworking.registerGlobalReceiver(CSMessages.DEALT_DAMAGE, DealtDamageS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CSMessages.JUMPPAD_UPDATED_POWER, JumppadPowerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(CSMessages.SHOW_RAILGUN_TRAIL, ShowRailgunTrailS2CPacket::receive);
    }

    public static void registerServerPackets()
    {
        ServerPlayNetworking.registerGlobalReceiver(CSMessages.GAUNTLET_DAMAGE, DealtGauntletDamageC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(CSMessages.JUMPPAD_UPDATE_POWER, JumppadPowerC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(CSMessages.QUAKE_MODE_UPDATE, QuakeModeUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(CSMessages.PLAYER_CLASS_UPDATE, PlayerClassUpdateC2SPacket::receive);
    }
}
