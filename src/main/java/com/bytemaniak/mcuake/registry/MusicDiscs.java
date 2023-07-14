package com.bytemaniak.mcuake.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class MusicDiscs {
    private static final Identifier THRU_THE_MIRROR_IDENT = new Identifier("mcuake", "thru_the_mirror");

    private static void LoadDisc(Identifier id, int comparatorOutput) {
        MusicDiscItem musicDisc = new MusicDiscItem(comparatorOutput, SoundEvent.of(id), new Item.Settings().maxCount(1), 0);
        Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
        Registry.register(Registries.ITEM, id, musicDisc);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.add(musicDisc));
        ItemGroupEvents.modifyEntriesEvent(Items.MCUAKE_GROUP).register(content -> content.add(musicDisc));
    }

    public static void LoadDiscs()
    {
        LoadDisc(THRU_THE_MIRROR_IDENT, 1);
    }

}
