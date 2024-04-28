package com.bytemaniak.mcquake3.gui;

import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.sound.SoundUtils;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

import java.util.LinkedList;

public class FeedbackManager implements HudRenderCallback {
    public enum Event {
        PLAYER_KILL,
        WEAPON_HIT
    }

    private enum MedalType {
        EXCELLENT,
        IMPRESSIVE,
        GAUNTLET
    }

    private record Medal(MedalType type, int count, int ch) {}

    private static final long MEDAL_UPDATE_RATE = MiscUtils.toTicks(3);
    private final LinkedList<Medal> medals = new LinkedList<>();
    private Medal currentMedal = null;
    private long lastUpdateTick = 0;

    private static final long FRAG_TIME_THRESHOLD = MiscUtils.toTicks(2);
    private long killTick = 0;
    private int killCount = 0;
    private int gauntletKills = 0;

    public boolean lastHitRailgun = false;
    private int railHitCount = 0;

    public void pushEvent(Event ev, boolean arg) {
        long currentTick = MinecraftClient.getInstance().world.getTime();

        switch (ev) {
            case PLAYER_KILL -> {
                long prevKillTick = killTick;
                killTick = currentTick;
                if (killTick - prevKillTick <= FRAG_TIME_THRESHOLD) {
                    killCount++;
                    medals.add(new Medal(MedalType.EXCELLENT, killCount, '\uFFFA'));
                }
                if (arg) {
                    gauntletKills++;
                    medals.add(new Medal(MedalType.GAUNTLET, gauntletKills, '\uFFFC'));
                }
            }
            case WEAPON_HIT -> {
                if (lastHitRailgun && arg) {
                    railHitCount++;
                    medals.add(new Medal(MedalType.IMPRESSIVE, railHitCount, '\uFFFB'));
                }

                lastHitRailgun = arg;
            }
        }
    }

    private void renderCurrentMedal(MatrixStack matrixStack) {
        if (currentMedal == null) return;

        Window window = MinecraftClient.getInstance().getWindow();
        String medal = Character.toString(currentMedal.ch).repeat(Math.min(currentMedal.count, 8));
        MiscUtils.drawText(matrixStack, medal, (float)window.getScaledWidth()/2 - medal.length()*16, (float)window.getScaledHeight()/15, 0xFFFFFF);
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        long currentTick = MinecraftClient.getInstance().world.getTime();

        if (currentTick - lastUpdateTick > MEDAL_UPDATE_RATE) {
            if (medals.isEmpty()) currentMedal = null;
            else {
                lastUpdateTick = currentTick;
                currentMedal = medals.remove();
                switch (currentMedal.type) {
                    case EXCELLENT -> SoundUtils.playSoundLocally(Sounds.EXCELLENT);
                    case IMPRESSIVE -> SoundUtils.playSoundLocally(Sounds.IMPRESSIVE);
                    case GAUNTLET -> SoundUtils.playSoundLocally(Sounds.GAUNTLET);
                }
            }
        }

        renderCurrentMedal(matrixStack);
    }
}
