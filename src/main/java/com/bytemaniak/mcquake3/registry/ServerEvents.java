package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.network.events.FeedbackSender;
import com.bytemaniak.mcquake3.network.events.MessageDing;
import com.bytemaniak.mcquake3.network.events.QuakeMatchState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ServerEvents {
    private static final FeedbackSender FEEDBACK_SENDER = new FeedbackSender();
    public static final QuakeMatchState QUAKE_MATCH_STATE = new QuakeMatchState();

    public static void registerEvents() {
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(FEEDBACK_SENDER);
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(FEEDBACK_SENDER);
        ServerTickEvents.START_WORLD_TICK.register(QUAKE_MATCH_STATE);
    }
}
