package com.bytemaniak.mcuake.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class Sounds {
    private static final Identifier JUMPPAD_BOOST_IDENT = new Identifier("mcuake", "jumppad");
    public static final SoundEvent JUMPPAD_BOOST = SoundEvent.of(JUMPPAD_BOOST_IDENT);
    private static final Identifier DAMAGE_DEALT_IDENT = new Identifier("mcuake", "hit");
    public static final SoundEvent DAMAGE_DEALT = SoundEvent.of(DAMAGE_DEALT_IDENT);

    private static final Identifier MACHINEGUN_FIRE_IDENT = new Identifier("mcuake", "machinegun");
    public static final SoundEvent MACHINEGUN_FIRE = SoundEvent.of(MACHINEGUN_FIRE_IDENT);

    private static final Identifier PLASMAGUN_FIRE_IDENT = new Identifier("mcuake", "plasmagun");
    public static final SoundEvent PLASMAGUN_FIRE = SoundEvent.of(PLASMAGUN_FIRE_IDENT);

    private static final Identifier LIGHTNING_FIRE_IDENT = new Identifier("mcuake", "lightning_gun");
    public static final SoundEvent LIGHTNING_FIRE = SoundEvent.of(LIGHTNING_FIRE_IDENT);
    private static final Identifier LIGHTNING_ACTIVE_IDENT = new Identifier("mcuake", "lightning_active");
    public static final SoundEvent LIGHTNING_ACTIVE = SoundEvent.of(LIGHTNING_ACTIVE_IDENT);

    private static final Identifier RAILGUN_FIRE_IDENT = new Identifier("mcuake", "railgun");
    public static final SoundEvent RAILGUN_FIRE = SoundEvent.of(RAILGUN_FIRE_IDENT);
    private static final Identifier RAILGUN_HUM_IDENT = new Identifier("mcuake", "railgun_idle");
    public static final SoundEvent RAILGUN_HUM = SoundEvent.of(RAILGUN_HUM_IDENT);

    private static final Identifier SHOTGUN_FIRE_IDENT = new Identifier("mcuake", "shotgun");
    public static final SoundEvent SHOTGUN_FIRE = SoundEvent.of(SHOTGUN_FIRE_IDENT);

    private static final Identifier GAUNTLET_DAMAGE_IDENT = new Identifier("mcuake", "gauntlet_damage");
    public static final SoundEvent GAUNTLET_DAMAGE = SoundEvent.of(GAUNTLET_DAMAGE_IDENT);
    private static final Identifier GAUNTLET_ACTIVE_IDENT = new Identifier("mcuake", "gauntlet_active");
    public static final SoundEvent GAUNTLET_ACTIVE = SoundEvent.of(GAUNTLET_ACTIVE_IDENT);
    private static final Identifier GAUNTLET_HUM_IDENT = new Identifier("mcuake", "gauntlet_idle");
    public static final SoundEvent GAUNTLET_HUM = SoundEvent.of(GAUNTLET_HUM_IDENT);

    private static final Identifier GRENADE_FIRE_IDENT = new Identifier("mcuake", "grenade_launcher");
    public static final SoundEvent GRENADE_FIRE = SoundEvent.of(GRENADE_FIRE_IDENT);

    public static class PlayerSounds {
        public Identifier DEATH;
        public Identifier DROWN;
        public Identifier FALL;
        public Identifier GASP;
        public Identifier JUMP;
        public Identifier HURT100;
        public Identifier HURT75;
        public Identifier HURT50;
        public Identifier HURT25;
        public Identifier TAUNT;

        public String playerClass;

        public PlayerSounds(String playerClass) {
            this.playerClass = playerClass;
            String ident = playerClass.toLowerCase();
            DEATH = new Identifier("mcuake", ident + "_death");
            DROWN = new Identifier("mcuake", ident + "_drown");
            FALL = new Identifier("mcuake", ident + "_fall");
            GASP = new Identifier("mcuake", ident + "_gasp");
            JUMP = new Identifier("mcuake", ident + "_jump");
            HURT100 = new Identifier("mcuake", ident + "_hurt100");
            HURT75 = new Identifier("mcuake", ident + "_hurt75");
            HURT50 = new Identifier("mcuake", ident + "_hurt50");
            HURT25 = new Identifier("mcuake", ident + "_hurt25");
            TAUNT = new Identifier("mcuake", ident + "_taunt");
        }
    }

    public static PlayerSounds ANGELYSS = new PlayerSounds("Angelyss");
    public static PlayerSounds ARACHNA = new PlayerSounds("Arachna");
    public static PlayerSounds ASSASSIN = new PlayerSounds("Assassin");
    public static PlayerSounds AYUMI = new PlayerSounds("Ayumi");
    public static PlayerSounds BERET = new PlayerSounds("Beret");
    public static PlayerSounds GARGOYLE = new PlayerSounds("Gargoyle");
    public static PlayerSounds KYONSHI = new PlayerSounds("Kyonshi");
    public static PlayerSounds LIZ = new PlayerSounds("Liz");
    public static PlayerSounds MAJOR = new PlayerSounds("Major");
    public static PlayerSounds MERMAN = new PlayerSounds("Merman");
    public static PlayerSounds NEKO = new PlayerSounds("Neko");
    public static PlayerSounds PENGUIN = new PlayerSounds("Penguin");
    public static PlayerSounds SARGE = new PlayerSounds("Sarge");
    public static PlayerSounds SERGEI = new PlayerSounds("Sergei");
    public static PlayerSounds SKELEBOT = new PlayerSounds("Skelebot");
    public static PlayerSounds SMARINE = new PlayerSounds("Smarine");
    public static PlayerSounds SORCERESS = new PlayerSounds("Sorceress");
    public static PlayerSounds TONY = new PlayerSounds("Tony");

    private static void loadPlayerSounds(PlayerSounds playerSounds) {
        Registry.register(Registries.SOUND_EVENT, playerSounds.DEATH, SoundEvent.of(playerSounds.DEATH));
        Registry.register(Registries.SOUND_EVENT, playerSounds.DROWN, SoundEvent.of(playerSounds.DROWN));
        Registry.register(Registries.SOUND_EVENT, playerSounds.FALL, SoundEvent.of(playerSounds.FALL));
        Registry.register(Registries.SOUND_EVENT, playerSounds.GASP, SoundEvent.of(playerSounds.GASP));
        Registry.register(Registries.SOUND_EVENT, playerSounds.JUMP, SoundEvent.of(playerSounds.JUMP));
        Registry.register(Registries.SOUND_EVENT, playerSounds.HURT100, SoundEvent.of(playerSounds.HURT100));
        Registry.register(Registries.SOUND_EVENT, playerSounds.HURT75, SoundEvent.of(playerSounds.HURT75));
        Registry.register(Registries.SOUND_EVENT, playerSounds.HURT50, SoundEvent.of(playerSounds.HURT50));
        Registry.register(Registries.SOUND_EVENT, playerSounds.HURT25, SoundEvent.of(playerSounds.HURT25));
        Registry.register(Registries.SOUND_EVENT, playerSounds.TAUNT, SoundEvent.of(playerSounds.TAUNT));
    }

    public static void loadSounds()
    {
        Registry.register(Registries.SOUND_EVENT, JUMPPAD_BOOST_IDENT, JUMPPAD_BOOST);
        Registry.register(Registries.SOUND_EVENT, DAMAGE_DEALT_IDENT, DAMAGE_DEALT);
        Registry.register(Registries.SOUND_EVENT, MACHINEGUN_FIRE_IDENT, MACHINEGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, PLASMAGUN_FIRE_IDENT, PLASMAGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, LIGHTNING_FIRE_IDENT, LIGHTNING_FIRE);
        Registry.register(Registries.SOUND_EVENT, LIGHTNING_ACTIVE_IDENT, LIGHTNING_ACTIVE);
        Registry.register(Registries.SOUND_EVENT, RAILGUN_FIRE_IDENT, RAILGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, RAILGUN_HUM_IDENT, RAILGUN_HUM);
        Registry.register(Registries.SOUND_EVENT, SHOTGUN_FIRE_IDENT, SHOTGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, GAUNTLET_DAMAGE_IDENT, GAUNTLET_DAMAGE);
        Registry.register(Registries.SOUND_EVENT, GAUNTLET_ACTIVE_IDENT, GAUNTLET_ACTIVE);
        Registry.register(Registries.SOUND_EVENT, GAUNTLET_HUM_IDENT, GAUNTLET_HUM);
        Registry.register(Registries.SOUND_EVENT, GRENADE_FIRE_IDENT, GRENADE_FIRE);
        loadPlayerSounds(ANGELYSS);
        loadPlayerSounds(ARACHNA);
        loadPlayerSounds(ASSASSIN);
        loadPlayerSounds(AYUMI);
        loadPlayerSounds(BERET);
        loadPlayerSounds(GARGOYLE);
        loadPlayerSounds(KYONSHI);
        loadPlayerSounds(LIZ);
        loadPlayerSounds(MAJOR);
        loadPlayerSounds(MERMAN);
        loadPlayerSounds(NEKO);
        loadPlayerSounds(PENGUIN);
        loadPlayerSounds(SARGE);
        loadPlayerSounds(SERGEI);
        loadPlayerSounds(SKELEBOT);
        loadPlayerSounds(SMARINE);
        loadPlayerSounds(SORCERESS);
        loadPlayerSounds(TONY);
    }
}
