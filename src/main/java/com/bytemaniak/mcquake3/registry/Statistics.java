package com.bytemaniak.mcquake3.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class Statistics {
    private static final String EXCELLENT = "excellent_medals";
    public static final Identifier EXCELLENT_MEDALS = new Identifier("mcquake3:"+EXCELLENT);
    public static final ScoreboardCriterion EXCELLENT_CRITERIA = ScoreboardCriterion.create("mcquake3."+EXCELLENT);
    private static final String IMPRESSIVE = "impressive_medals";
    public static final Identifier IMPRESSIVE_MEDALS = new Identifier("mcquake3:"+IMPRESSIVE);
    public static final ScoreboardCriterion IMPRESSIVE_CRITERIA = ScoreboardCriterion.create("mcquake3."+IMPRESSIVE);
    private static final String GAUNTLET = "gauntlet_medals";
    public static final Identifier GAUNTLET_MEDALS = new Identifier("mcquake3:"+GAUNTLET);
    public static final ScoreboardCriterion GAUNTLET_CRITERIA = ScoreboardCriterion.create("mcquake3."+GAUNTLET);

    private static final String Q3_MATCH_FRAGS = "q3_match_frags";
    public static final Identifier Q3_MATCH_FRAGS_IDENT = new Identifier("mcquake3:"+Q3_MATCH_FRAGS);
    public static final ScoreboardCriterion Q3_MATCH_FRAGS_CRITERIA = ScoreboardCriterion.create("mcquake3."+Q3_MATCH_FRAGS);
    private static final String Q3_MATCHES_WON = "q3_matches_won";
    public static final Identifier Q3_MATCHES_WON_IDENT = new Identifier("mcquake3:"+Q3_MATCHES_WON);
    private static final String Q3_MATCHES_LOST = "q3_matches_lost";
    public static final Identifier Q3_MATCHES_LOST_IDENT = new Identifier("mcquake3:"+Q3_MATCHES_LOST);

    private static void registerStat(String s, Identifier id) {
        Registry.register(Registries.CUSTOM_STAT, s, id);
        Stats.CUSTOM.getOrCreateStat(id);
    }

    public static void registerStats() {
        registerStat(EXCELLENT, EXCELLENT_MEDALS);
        registerStat(IMPRESSIVE, IMPRESSIVE_MEDALS);
        registerStat(GAUNTLET, GAUNTLET_MEDALS);
        registerStat(Q3_MATCH_FRAGS, Q3_MATCH_FRAGS_IDENT);
        registerStat(Q3_MATCHES_WON, Q3_MATCHES_WON_IDENT);
        registerStat(Q3_MATCHES_LOST, Q3_MATCHES_LOST_IDENT);
    }
}
