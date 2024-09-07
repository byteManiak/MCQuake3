package com.bytemaniak.mcquake3.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class MusicDiscs {
    private static int comparatorOutput = 0;

    private static final Identifier KOJINSMOKIN_IDENT = Identifier.of("mcquake3:disc_kojinsmokin");
    public static final SoundEvent KOJINSMOKIN = SoundEvent.of(KOJINSMOKIN_IDENT);
    private static final Identifier NINESIX_IDENT = Identifier.of("mcquake3:disc_ninesix");
    public static final SoundEvent NINESIX = SoundEvent.of(NINESIX_IDENT);
    private static final Identifier SIXTYFOUR_IDENT = Identifier.of("mcquake3:disc_sixtyfour");
    public static final SoundEvent SIXTYFOUR = SoundEvent.of(SIXTYFOUR_IDENT);
    private static final Identifier STAIRS_IDENT = Identifier.of("mcquake3:disc_stairs");
    public static final SoundEvent STAIRS = SoundEvent.of(STAIRS_IDENT);

    private static void loadDisc(Identifier id, SoundEvent sound, int seconds) {
        ///MusicDiscItem musicDisc = new MusicDiscItem(++comparatorOutput, SoundEvent.of(id), new Item.Settings().maxCount(1), seconds);
        Registry.register(Registries.SOUND_EVENT, id, sound);
        ///Registry.register(Registries.ITEM, id, musicDisc);
        ///ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.add(musicDisc));
    }

    public static void loadDiscs() {
        loadDisc(KOJINSMOKIN_IDENT, KOJINSMOKIN, 278);
        loadDisc(NINESIX_IDENT, NINESIX, 206);
        loadDisc(SIXTYFOUR_IDENT, SIXTYFOUR, 208);
        loadDisc(STAIRS_IDENT, STAIRS, 167);
    }
}
