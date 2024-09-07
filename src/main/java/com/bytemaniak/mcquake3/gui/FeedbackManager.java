package com.bytemaniak.mcquake3.gui;

import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.sound.SoundUtils;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.network.PacketByteBuf;

import java.util.concurrent.CopyOnWriteArrayList;

public class FeedbackManager implements HudRenderCallback {
    public enum Event {
        PLAYER_KILL,
        WEAPON_HIT
    }

    private enum MedalType {
        EXCELLENT(1),
        IMPRESSIVE(2),
        GAUNTLET(3);

        public final byte value;
        MedalType(int value) { this.value = (byte)value; }
    }

    private record Medal(MedalType type, int count, int ch) {}

    private static final long MEDAL_UPDATE_RATE = MiscUtils.toTicks(3);
    private final CopyOnWriteArrayList<Medal> medals = new CopyOnWriteArrayList<>();
    private Medal currentMedal = null;
    private long lastUpdateTick = 0;

    private static final long FRAG_TIME_THRESHOLD = MiscUtils.toTicks(2);
    private long killTick = 0;
    private int killCount = 0;
    private int gauntletKills = 0;

    public boolean lastHitRailgun = false;
    private int railHitCount = 0;

    private void addMedal(MedalType medal, int value, int ch) {
        medals.add(new Medal(medal, value, ch));
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(medal.value);
        ///ClientPlayNetworking.send(Packets.ADD_MEDAL, buf);
    }

    public void pushEvent(Event ev, boolean arg) {
        long currentTick = MinecraftClient.getInstance().world.getTime();

        switch (ev) {
            case PLAYER_KILL -> {
                long prevKillTick = killTick;
                killTick = currentTick;
                if (killTick - prevKillTick <= FRAG_TIME_THRESHOLD) {
                    killCount++;
                    addMedal(MedalType.EXCELLENT, killCount, '\uFFFA');
                }
                if (arg) {
                    gauntletKills++;
                    addMedal(MedalType.GAUNTLET, gauntletKills, '\uFFFC');
                }
            }
            case WEAPON_HIT -> {
                if (lastHitRailgun && arg) {
                    railHitCount++;
                    addMedal(MedalType.IMPRESSIVE, railHitCount, '\uFFFB');
                }

                lastHitRailgun = arg;
            }
        }
    }

    private void renderCurrentMedal(DrawContext context) {
        if (currentMedal == null) return;

        Window window = MinecraftClient.getInstance().getWindow();
        String medal = Character.toString(currentMedal.ch).repeat(Math.min(currentMedal.count, 8));
        MiscUtils.drawText(context, medal, window.getScaledWidth()/2 - medal.length()*16, window.getScaledHeight()/15, 0xFFFFFF);
    }

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        long currentTick = MinecraftClient.getInstance().world.getTime();

        if (currentTick - lastUpdateTick > MEDAL_UPDATE_RATE) {
            if (medals.isEmpty()) currentMedal = null;
            else {
                lastUpdateTick = currentTick;
                currentMedal = medals.remove(0);
                switch (currentMedal.type) {
                    case EXCELLENT -> SoundUtils.playSoundLocally(Sounds.EXCELLENT);
                    case IMPRESSIVE -> SoundUtils.playSoundLocally(Sounds.IMPRESSIVE);
                    case GAUNTLET -> SoundUtils.playSoundLocally(Sounds.GAUNTLET);
                }
            }
        }

        renderCurrentMedal(context);
    }
}
