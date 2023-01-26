package com.bytemaniak.mcuake.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class Sounds {
    private static final Identifier DAMAGE_DEALT_IDENT = new Identifier("mcuake", "hit");
    public static final SoundEvent DAMAGE_DEALT = SoundEvent.of(DAMAGE_DEALT_IDENT);

    private static final Identifier MACHINEGUN_FIRE_IDENT = new Identifier("mcuake", "machinegun");
    public static final SoundEvent MACHINEGUN_FIRE = SoundEvent.of(MACHINEGUN_FIRE_IDENT);

    private static final Identifier PLASMAGUN_FIRE_IDENT = new Identifier("mcuake", "plasmagun");
    public static final SoundEvent PLASMAGUN_FIRE = SoundEvent.of(PLASMAGUN_FIRE_IDENT);

    private static final Identifier RAILGUN_FIRE_IDENT = new Identifier("mcuake", "railgun");
    public static final SoundEvent RAILGUN_FIRE = SoundEvent.of(RAILGUN_FIRE_IDENT);

    public static void loadSounds()
    {
        Registry.register(Registries.SOUND_EVENT, DAMAGE_DEALT_IDENT, DAMAGE_DEALT);
        Registry.register(Registries.SOUND_EVENT, MACHINEGUN_FIRE_IDENT, MACHINEGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, PLASMAGUN_FIRE_IDENT, PLASMAGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, RAILGUN_FIRE_IDENT, RAILGUN_FIRE);
    }
}
