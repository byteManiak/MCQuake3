package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.network.c2s.*;
import com.bytemaniak.mcquake3.network.s2c.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class Packets {
    public static final Identifier JUMPPAD_UPDATED_POWER = new Identifier("mcquake3:jumppad_updated_power");
    public static final Identifier DEALT_DAMAGE = new Identifier("mcquake3:dealt_damage");
    public static final Identifier KILLED_PLAYER = new Identifier("mcquake3:killed_player");
    public static final Identifier AMMO_BOX_UPDATE = new Identifier("mcquake3:ammo_box_update");
    public static final Identifier PLAYER_AMMO_TRAIL_FIX = new Identifier("mcquake3:trail_fix");
    public static final Identifier SCROLL_TO_SLOT = new Identifier("mcquake3:scroll_to_slot");

    public static final Identifier JUMPPAD_UPDATE_POWER = new Identifier("mcquake3:jumppad_update_power");
    public static final Identifier WEAPON_REFIRE_UPDATE = new Identifier("mcquake3:weapon_refire_update");
    public static final Identifier PLAYER_CLASS_UPDATE = new Identifier("mcquake3:player_class_update");
    public static final Identifier FULL_ARSENAL_REQUEST = new Identifier("mcquake3:full_arsenal_request");
    public static final Identifier PLAYER_TAUNT = new Identifier("mcquake3:player_taunt");
    public static final Identifier ADD_MEDAL = new Identifier("mcquake3:add_medal");
    public static final Identifier JUMPPAD_SOUND = new Identifier("mcquake3:jumppad_sound");
    public static final Identifier REQUEST_JOIN_MATCH = new Identifier("mcquake3:join_match");
    public static final Identifier MAP_SELECT_DELETE = new Identifier("mcquake3:map_select_delete");

    public static void registerClientPackets() {
        ClientPlayNetworking.registerGlobalReceiver(JUMPPAD_UPDATED_POWER, JumppadPowerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(DEALT_DAMAGE, DealtDamageS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(KILLED_PLAYER, KilledPlayerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(AMMO_BOX_UPDATE, PickupVisibilityS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PLAYER_AMMO_TRAIL_FIX, PlayerAmmoUpdateTrailFixS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SCROLL_TO_SLOT, ScrollToSlotS2CPacket::receive);
    }

    public static void registerServerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(JUMPPAD_UPDATE_POWER, JumppadPowerC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(WEAPON_REFIRE_UPDATE, WeaponRefireUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PLAYER_CLASS_UPDATE, PlayerClassUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(FULL_ARSENAL_REQUEST, FullArsenalC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PLAYER_TAUNT, PlayerTauntC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ADD_MEDAL, AddMedalC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(JUMPPAD_SOUND, JumppadSoundC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_JOIN_MATCH, JoinMatchC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(MAP_SELECT_DELETE, MapSelectDeleteC2SPacket::receive);
    }
}
