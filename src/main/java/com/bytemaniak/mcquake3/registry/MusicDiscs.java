package com.bytemaniak.mcquake3.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class MusicDiscs {
    private static final Identifier KOJINSMOKIN_IDENT = Identifier.of("mcquake3:disc_kojinsmokin");
    public static final SoundEvent KOJINSMOKIN = SoundEvent.of(KOJINSMOKIN_IDENT);
    private static final Identifier NINESIX_IDENT = Identifier.of("mcquake3:disc_ninesix");
    public static final SoundEvent NINESIX = SoundEvent.of(NINESIX_IDENT);
    private static final Identifier SIXTYFOUR_IDENT = Identifier.of("mcquake3:disc_sixtyfour");
    public static final SoundEvent SIXTYFOUR = SoundEvent.of(SIXTYFOUR_IDENT);
    private static final Identifier STAIRS_IDENT = Identifier.of("mcquake3:disc_stairs");
    public static final SoundEvent STAIRS = SoundEvent.of(STAIRS_IDENT);

    private static void loadDisc(Identifier id, SoundEvent sound) {
        RegistryKey<JukeboxSong> key = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, id);
        Item musicDisc = new Item(new Item.Settings().rarity(Rarity.RARE).maxCount(1).jukeboxPlayable(key));
        Registry.register(Registries.SOUND_EVENT, id, sound);
        Registry.register(Registries.ITEM, id, musicDisc);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.add(musicDisc));
    }

    public static void loadDiscs() {
        loadDisc(KOJINSMOKIN_IDENT, KOJINSMOKIN);
        loadDisc(NINESIX_IDENT, NINESIX);
        loadDisc(SIXTYFOUR_IDENT, SIXTYFOUR);
        loadDisc(STAIRS_IDENT, STAIRS);
    }
}
