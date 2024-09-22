package com.bytemaniak.mcquake3.network.events;

import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class CopyPlayerData implements ServerPlayerEvents.CopyFrom {
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        QuakePlayer newQuakePlayer = (QuakePlayer) newPlayer;
        QuakePlayer oldQuakePlayer = (QuakePlayer) oldPlayer;
        newQuakePlayer.mcquake3$setPlayerVoice(oldQuakePlayer.mcquake3$getPlayerVoice());
    }
}
