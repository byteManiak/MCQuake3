package com.bytemaniak.mcquake3.network.events;

import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.sound.SoundUtils;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class MessageDing implements ClientReceiveMessageEvents.Chat {
    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        SoundUtils.playSoundLocally(Sounds.DING);
    }
}
