package com.bytemaniak.mcquake3.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class Statistics {
    private static final String EXCELLENT = "excellent_medals";
    public static final Identifier EXCELLENT_MEDALS = Identifier.of("mcquake3:"+EXCELLENT);
    public static final ScoreboardCriterion EXCELLENT_CRITERIA = ScoreboardCriterion.create("mcquake3."+EXCELLENT);
    private static final String IMPRESSIVE = "impressive_medals";
    public static final Identifier IMPRESSIVE_MEDALS = Identifier.of("mcquake3:"+IMPRESSIVE);
    public static final ScoreboardCriterion IMPRESSIVE_CRITERIA = ScoreboardCriterion.create("mcquake3."+IMPRESSIVE);
    private static final String GAUNTLET = "gauntlet_medals";
    public static final Identifier GAUNTLET_MEDALS = Identifier.of("mcquake3:"+GAUNTLET);
    public static final ScoreboardCriterion GAUNTLET_CRITERIA = ScoreboardCriterion.create("mcquake3."+GAUNTLET);

    private static void registerStat(String s, Identifier id) {
        Registry.register(Registries.CUSTOM_STAT, s, id);
        Stats.CUSTOM.getOrCreateStat(id);
    }

    public static void registerStats() {
        registerStat(EXCELLENT, EXCELLENT_MEDALS);
        registerStat(IMPRESSIVE, IMPRESSIVE_MEDALS);
        registerStat(GAUNTLET, GAUNTLET_MEDALS);
    }
}
