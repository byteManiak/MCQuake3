package com.bytemaniak.mcquake3.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class Sounds {
    private static final Identifier JUMPPAD_BOOST_IDENT = new Identifier("mcquake3:jumppad");
    public static final SoundEvent JUMPPAD_BOOST = SoundEvent.of(JUMPPAD_BOOST_IDENT);

    private static final Identifier DAMAGE_DEALT_IDENT = new Identifier("mcquake3:hit");
    public static final SoundEvent DAMAGE_DEALT = SoundEvent.of(DAMAGE_DEALT_IDENT);
    private static final Identifier EXCELLENT_IDENT = new Identifier("mcquake3:excellent");
    public static final SoundEvent EXCELLENT = SoundEvent.of(EXCELLENT_IDENT);
    private static final Identifier IMPRESSIVE_IDENT = new Identifier("mcquake3:impressive");
    public static final SoundEvent IMPRESSIVE = SoundEvent.of(IMPRESSIVE_IDENT);

    private static final Identifier MACHINEGUN_FIRE_IDENT = new Identifier("mcquake3:machinegun");
    public static final SoundEvent MACHINEGUN_FIRE = SoundEvent.of(MACHINEGUN_FIRE_IDENT);

    private static final Identifier PLASMAGUN_FIRE_IDENT = new Identifier("mcquake3:plasmagun");
    public static final SoundEvent PLASMAGUN_FIRE = SoundEvent.of(PLASMAGUN_FIRE_IDENT);

    private static final Identifier LIGHTNING_FIRE_IDENT = new Identifier("mcquake3:lightning_gun");
    public static final SoundEvent LIGHTNING_FIRE = SoundEvent.of(LIGHTNING_FIRE_IDENT);
    private static final Identifier LIGHTNING_ACTIVE_IDENT = new Identifier("mcquake3:lightning_active");
    public static final SoundEvent LIGHTNING_ACTIVE = SoundEvent.of(LIGHTNING_ACTIVE_IDENT);

    private static final Identifier RAILGUN_FIRE_IDENT = new Identifier("mcquake3:railgun");
    public static final SoundEvent RAILGUN_FIRE = SoundEvent.of(RAILGUN_FIRE_IDENT);
    private static final Identifier RAILGUN_HUM_IDENT = new Identifier("mcquake3:railgun_idle");
    public static final SoundEvent RAILGUN_HUM = SoundEvent.of(RAILGUN_HUM_IDENT);

    private static final Identifier SHOTGUN_FIRE_IDENT = new Identifier("mcquake3:shotgun");
    public static final SoundEvent SHOTGUN_FIRE = SoundEvent.of(SHOTGUN_FIRE_IDENT);

    private static final Identifier GAUNTLET_DAMAGE_IDENT = new Identifier("mcquake3:gauntlet_damage");
    public static final SoundEvent GAUNTLET_DAMAGE = SoundEvent.of(GAUNTLET_DAMAGE_IDENT);
    private static final Identifier GAUNTLET_ACTIVE_IDENT = new Identifier("mcquake3:gauntlet_active");
    public static final SoundEvent GAUNTLET_ACTIVE = SoundEvent.of(GAUNTLET_ACTIVE_IDENT);
    private static final Identifier GAUNTLET_HUM_IDENT = new Identifier("mcquake3:gauntlet_idle");
    public static final SoundEvent GAUNTLET_HUM = SoundEvent.of(GAUNTLET_HUM_IDENT);

    private static final Identifier BFG10K_FIRE_IDENT = new Identifier("mcquake3:bfg10k");
    public static final SoundEvent BFG10K_FIRE = SoundEvent.of(BFG10K_FIRE_IDENT);

    private static final Identifier GRENADE_FIRE_IDENT = new Identifier("mcquake3:grenade_launcher");
    public static final SoundEvent GRENADE_FIRE = SoundEvent.of(GRENADE_FIRE_IDENT);
    private static final Identifier GRENADE_BOUNCE_IDENT = new Identifier("mcquake3:grenade_bounce");
    public static final SoundEvent GRENADE_BOUNCE = SoundEvent.of(GRENADE_BOUNCE_IDENT);

    private static final Identifier ROCKET_FLYING_IDENT = new Identifier("mcquake3:rocket_flying");
    public static final SoundEvent ROCKET_FLYING = SoundEvent.of(ROCKET_FLYING_IDENT);

    private static final Identifier PLASMABALL_FLYING_IDENT = new Identifier("mcquake3:plasmaball_flying");
    public static final SoundEvent PLASMABALL_FLYING = SoundEvent.of(PLASMABALL_FLYING_IDENT);
    private static final Identifier PLASMABALL_HIT_IDENT = new Identifier("mcquake3:plasmaball_hit");
    public static final SoundEvent PLASMABALL_HIT = SoundEvent.of(PLASMABALL_HIT_IDENT);

    private static final Identifier LIGHTNING_HIT_IDENT = new Identifier("mcquake3:lightning_hit");
    public static final SoundEvent LIGHTNING_HIT = SoundEvent.of(LIGHTNING_HIT_IDENT);

    private static final Identifier BULLET_MISS_IDENT = new Identifier("mcquake3:bullet_miss");
    public static final SoundEvent BULLET_MISS = SoundEvent.of(BULLET_MISS_IDENT);

    private static final Identifier WEAPON_CHANGE_IDENT = new Identifier("mcquake3:weapon_change");
    public static final SoundEvent WEAPON_CHANGE = SoundEvent.of(WEAPON_CHANGE_IDENT);

    private static final Identifier REGEN_IDENT = new Identifier("mcquake3:regen");
    public static final SoundEvent REGEN = SoundEvent.of(REGEN_IDENT);
    private static final Identifier POWERUP_REGEN_IDENT = new Identifier("mcquake3:powerup_regen");
    public static final SoundEvent POWERUP_REGEN = SoundEvent.of(POWERUP_REGEN_IDENT);

    private static final Identifier HASTE_IDENT = new Identifier("mcquake3:haste");
    public static final SoundEvent HASTE = SoundEvent.of(HASTE_IDENT);
    private static final Identifier INVISIBILITY_IDENT = new Identifier("mcquake3:invisibility");
    public static final SoundEvent INVISIBILITY = SoundEvent.of(INVISIBILITY_IDENT);
    private static final Identifier QUAD_DAMAGE_IDENT = new Identifier("mcquake3:quad_damage");
    public static final SoundEvent QUAD_DAMAGE = SoundEvent.of(QUAD_DAMAGE_IDENT);

    private static final Identifier AMMO_PICKUP_IDENT = new Identifier("mcquake3:ammo_pickup");
    public static final SoundEvent AMMO_PICKUP = SoundEvent.of(AMMO_PICKUP_IDENT);

    private static final Identifier WEAPON_PICKUP_IDENT = new Identifier("mcquake3:weapon_pickup");
    public static final SoundEvent WEAPON_PICKUP = SoundEvent.of(WEAPON_PICKUP_IDENT);

    private static final Identifier HEALTH5_IDENT = new Identifier("mcquake3:5health");
    public static final SoundEvent HEALTH5 = SoundEvent.of(HEALTH5_IDENT);

    private static final Identifier HEALTH25_IDENT = new Identifier("mcquake3:25health");
    public static final SoundEvent HEALTH25 = SoundEvent.of(HEALTH25_IDENT);

    private static final Identifier HEALTH50_IDENT = new Identifier("mcquake3:50health");
    public static final SoundEvent HEALTH50 = SoundEvent.of(HEALTH50_IDENT);

    private static final Identifier SHIELD_CELL_IDENT = new Identifier("mcquake3:energy_shield_cell");
    public static final SoundEvent SHIELD_CELL = SoundEvent.of(SHIELD_CELL_IDENT);

    private static final Identifier LIGHT_ENERGY_SHIELD_IDENT = new Identifier("mcquake3:light_energy_shield");
    public static final SoundEvent LIGHT_ENERGY_SHIELD = SoundEvent.of(LIGHT_ENERGY_SHIELD_IDENT);

    private static final Identifier HEAVY_ENERGY_SHIELD_IDENT = new Identifier("mcquake3:heavy_energy_shield");
    public static final SoundEvent HEAVY_ENERGY_SHIELD = SoundEvent.of(HEAVY_ENERGY_SHIELD_IDENT);

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
            DEATH = new Identifier("mcquake3", ident + "_death");
            DROWN = new Identifier("mcquake3", ident + "_drown");
            FALL = new Identifier("mcquake3", ident + "_fall");
            GASP = new Identifier("mcquake3", ident + "_gasp");
            JUMP = new Identifier("mcquake3", ident + "_jump");
            HURT100 = new Identifier("mcquake3", ident + "_hurt100");
            HURT75 = new Identifier("mcquake3", ident + "_hurt75");
            HURT50 = new Identifier("mcquake3", ident + "_hurt50");
            HURT25 = new Identifier("mcquake3", ident + "_hurt25");
            TAUNT = new Identifier("mcquake3", ident + "_taunt");
        }
    }

    public static PlayerSounds NONE = new PlayerSounds("Vanilla");
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
        Registry.register(Registries.SOUND_EVENT, EXCELLENT_IDENT, EXCELLENT);
        Registry.register(Registries.SOUND_EVENT, IMPRESSIVE_IDENT, IMPRESSIVE);
        Registry.register(Registries.SOUND_EVENT, MACHINEGUN_FIRE_IDENT, MACHINEGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, PLASMAGUN_FIRE_IDENT, PLASMAGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, LIGHTNING_FIRE_IDENT, LIGHTNING_FIRE);
        Registry.register(Registries.SOUND_EVENT, LIGHTNING_ACTIVE_IDENT, LIGHTNING_ACTIVE);
        Registry.register(Registries.SOUND_EVENT, RAILGUN_FIRE_IDENT, RAILGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, RAILGUN_HUM_IDENT, RAILGUN_HUM);
        Registry.register(Registries.SOUND_EVENT, SHOTGUN_FIRE_IDENT, SHOTGUN_FIRE);
        Registry.register(Registries.SOUND_EVENT, BFG10K_FIRE_IDENT, BFG10K_FIRE);
        Registry.register(Registries.SOUND_EVENT, GAUNTLET_DAMAGE_IDENT, GAUNTLET_DAMAGE);
        Registry.register(Registries.SOUND_EVENT, GAUNTLET_ACTIVE_IDENT, GAUNTLET_ACTIVE);
        Registry.register(Registries.SOUND_EVENT, GAUNTLET_HUM_IDENT, GAUNTLET_HUM);
        Registry.register(Registries.SOUND_EVENT, GRENADE_FIRE_IDENT, GRENADE_FIRE);
        Registry.register(Registries.SOUND_EVENT, GRENADE_BOUNCE_IDENT, GRENADE_BOUNCE);
        Registry.register(Registries.SOUND_EVENT, ROCKET_FLYING_IDENT, ROCKET_FLYING);
        Registry.register(Registries.SOUND_EVENT, LIGHTNING_HIT_IDENT, LIGHTNING_HIT);
        Registry.register(Registries.SOUND_EVENT, BULLET_MISS_IDENT, BULLET_MISS);
        Registry.register(Registries.SOUND_EVENT, WEAPON_CHANGE_IDENT, WEAPON_CHANGE);
        Registry.register(Registries.SOUND_EVENT, REGEN_IDENT, REGEN);
        Registry.register(Registries.SOUND_EVENT, POWERUP_REGEN_IDENT, POWERUP_REGEN);
        Registry.register(Registries.SOUND_EVENT, HASTE_IDENT, HASTE);
        Registry.register(Registries.SOUND_EVENT, INVISIBILITY_IDENT, INVISIBILITY);
        Registry.register(Registries.SOUND_EVENT, QUAD_DAMAGE_IDENT, QUAD_DAMAGE);
        Registry.register(Registries.SOUND_EVENT, AMMO_PICKUP_IDENT, AMMO_PICKUP);
        Registry.register(Registries.SOUND_EVENT, WEAPON_PICKUP_IDENT, WEAPON_PICKUP);
        Registry.register(Registries.SOUND_EVENT, HEALTH5_IDENT, HEALTH5);
        Registry.register(Registries.SOUND_EVENT, HEALTH25_IDENT, HEALTH25);
        Registry.register(Registries.SOUND_EVENT, HEALTH50_IDENT, HEALTH50);
        Registry.register(Registries.SOUND_EVENT, SHIELD_CELL_IDENT, SHIELD_CELL);
        Registry.register(Registries.SOUND_EVENT, LIGHT_ENERGY_SHIELD_IDENT, LIGHT_ENERGY_SHIELD);
        Registry.register(Registries.SOUND_EVENT, HEAVY_ENERGY_SHIELD_IDENT, HEAVY_ENERGY_SHIELD);

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
