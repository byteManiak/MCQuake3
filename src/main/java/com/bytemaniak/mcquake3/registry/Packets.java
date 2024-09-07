package com.bytemaniak.mcquake3.registry;

import net.minecraft.util.Identifier;

public class Packets {
    public static final Identifier JUMPPAD_UPDATED_POWER = Identifier.of("mcquake3:jumppad_updated_power");
    public static final Identifier DEALT_DAMAGE = Identifier.of("mcquake3:dealt_damage");
    public static final Identifier KILLED_PLAYER = Identifier.of("mcquake3:killed_player");
    public static final Identifier AMMO_BOX_UPDATE = Identifier.of("mcquake3:ammo_box_update");
    public static final Identifier PLAYER_AMMO_TRAIL_FIX = Identifier.of("mcquake3:trail_fix");
    public static final Identifier SCROLL_TO_SLOT = Identifier.of("mcquake3:scroll_to_slot");
    public static final Identifier PLAY_ANNOUNCER_SOUND = Identifier.of("mcquake3:announcer_sound");
    public static final Identifier SEND_ARENA_NAMES = Identifier.of("mcquake3:send_arena_names");
    public static final Identifier FRAGS = Identifier.of("mcquake3:frags");

    public static final Identifier JUMPPAD_UPDATE_POWER = Identifier.of("mcquake3:jumppad_update_power");
    public static final Identifier WEAPON_REFIRE_UPDATE = Identifier.of("mcquake3:weapon_refire_update");
    public static final Identifier PLAYER_CLASS_UPDATE = Identifier.of("mcquake3:player_class_update");
    public static final Identifier FULL_ARSENAL_REQUEST = Identifier.of("mcquake3:full_arsenal_request");
    public static final Identifier PLAYER_TAUNT = Identifier.of("mcquake3:player_taunt");
    public static final Identifier ADD_MEDAL = Identifier.of("mcquake3:add_medal");
    public static final Identifier JUMPPAD_SOUND = Identifier.of("mcquake3:jumppad_sound");
    public static final Identifier JOIN_LEAVE_MATCH = Identifier.of("mcquake3:join_leave_match");
    public static final Identifier ARENA_SELECT_DELETE = Identifier.of("mcquake3:arena_select_delete");
    public static final Identifier GET_ARENA_NAMES = Identifier.of("mcquake3:get_arena_names");

    public static void registerClientPackets() {
        /*ClientPlayNetworking.registerGlobalReceiver(JUMPPAD_UPDATED_POWER, JumppadPowerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(DEALT_DAMAGE, DealtDamageS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(KILLED_PLAYER, KilledPlayerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(AMMO_BOX_UPDATE, PickupVisibilityS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PLAYER_AMMO_TRAIL_FIX, PlayerAmmoUpdateTrailFixS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SCROLL_TO_SLOT, ScrollToSlotS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PLAY_ANNOUNCER_SOUND, PlayAnnouncerSoundS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SEND_ARENA_NAMES, SendArenaNamesS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(FRAGS, FragsS2CPacket::receive);*/
    }

    public static void registerServerPackets() {
        /*ServerPlayNetworking.registerGlobalReceiver(JUMPPAD_UPDATE_POWER, JumppadPowerC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(WEAPON_REFIRE_UPDATE, WeaponRefireUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PLAYER_CLASS_UPDATE, PlayerClassUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(FULL_ARSENAL_REQUEST, FullArsenalC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PLAYER_TAUNT, PlayerTauntC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ADD_MEDAL, AddMedalC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(JUMPPAD_SOUND, JumppadSoundC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(JOIN_LEAVE_MATCH, JoinLeaveMatchS2CPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ARENA_SELECT_DELETE, ArenaSelectDeleteC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GET_ARENA_NAMES, GetArenaNamesC2SPacket::receive);*/
    }
}
