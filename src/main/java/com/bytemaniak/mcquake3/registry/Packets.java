package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.network.c2s.*;
import com.bytemaniak.mcquake3.network.s2c.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
    public static final Identifier PLAYER_TAUNT = Identifier.of("mcquake3:player_taunt");
    public static final Identifier ADD_MEDAL = Identifier.of("mcquake3:add_medal");
    public static final Identifier JUMPPAD_SOUND = Identifier.of("mcquake3:jumppad_sound");
    public static final Identifier JOIN_LEAVE_MATCH = Identifier.of("mcquake3:join_leave_match");
    public static final Identifier ARENA_SELECT_DELETE = Identifier.of("mcquake3:arena_select_delete");
    public static final Identifier GET_ARENA_NAMES = Identifier.of("mcquake3:get_arena_names");

    public static void registerClientPackets() {
        ClientPlayNetworking.registerGlobalReceiver(JumppadPowerS2CPacket.ID, JumppadPowerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(DealtDamageS2CPacket.ID, DealtDamageS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(KilledPlayerS2CPacket.ID, KilledPlayerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PickupVisibilityS2CPacket.ID, PickupVisibilityS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PlayerAmmoUpdateTrailFixS2CPacket.ID, PlayerAmmoUpdateTrailFixS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ScrollToSlotS2CPacket.ID, ScrollToSlotS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(PlayAnnouncerSoundS2CPacket.ID, PlayAnnouncerSoundS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendArenaNamesS2CPacket.ID, SendArenaNamesS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(FragsS2CPacket.ID, FragsS2CPacket::receive);
    }

    public static void registerPackets() {
        PayloadTypeRegistry.playS2C().register(JumppadPowerS2CPacket.ID, JumppadPowerS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(DealtDamageS2CPacket.ID, DealtDamageS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(KilledPlayerS2CPacket.ID, KilledPlayerS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(PickupVisibilityS2CPacket.ID, PickupVisibilityS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(PlayerAmmoUpdateTrailFixS2CPacket.ID, PlayerAmmoUpdateTrailFixS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ScrollToSlotS2CPacket.ID, ScrollToSlotS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(PlayAnnouncerSoundS2CPacket.ID, PlayAnnouncerSoundS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendArenaNamesS2CPacket.ID, SendArenaNamesS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(FragsS2CPacket.ID, FragsS2CPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(JumppadPowerC2SPacket.ID, JumppadPowerC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(WeaponRefireUpdateC2SPacket.ID, WeaponRefireUpdateC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(PlayerClassUpdateC2SPacket.ID, PlayerClassUpdateC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(PlayerTauntC2SPacket.ID, PlayerTauntC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(AddMedalC2SPacket.ID, AddMedalC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(JumppadSoundC2SPacket.ID, JumppadSoundC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(JoinLeaveMatchS2CPacket.ID, JoinLeaveMatchS2CPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ArenaSelectDeleteC2SPacket.ID, ArenaSelectDeleteC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(GetArenaNamesC2SPacket.ID, GetArenaNamesC2SPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(JumppadPowerC2SPacket.ID, JumppadPowerC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(WeaponRefireUpdateC2SPacket.ID, WeaponRefireUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PlayerClassUpdateC2SPacket.ID, PlayerClassUpdateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PlayerTauntC2SPacket.ID, PlayerTauntC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(AddMedalC2SPacket.ID, AddMedalC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(JumppadSoundC2SPacket.ID, JumppadSoundC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(JoinLeaveMatchS2CPacket.ID, JoinLeaveMatchS2CPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ArenaSelectDeleteC2SPacket.ID, ArenaSelectDeleteC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GetArenaNamesC2SPacket.ID, GetArenaNamesC2SPacket::receive);
    }
}
