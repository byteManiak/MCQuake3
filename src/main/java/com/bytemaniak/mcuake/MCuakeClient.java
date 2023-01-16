package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.blocks.JumppadScreen;
import com.bytemaniak.mcuake.cs.ClientReceivers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class MCuakeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient()
    {
        ClientReceivers.init();
        HandledScreens.register(MCuake.JUMPPAD_SCREEN_HANDLER, JumppadScreen::new);
    }
}
