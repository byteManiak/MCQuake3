package com.bytemaniak.mcquake3.registry.client;

import com.bytemaniak.mcquake3.network.events.MessageDing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

@Environment(EnvType.CLIENT)
public class ClientEvents {
    public static final MessageDing MESSAGE_DING = new MessageDing();

    public static void registerEvents() {
        ClientReceiveMessageEvents.CHAT.register(MESSAGE_DING);
    }
}
