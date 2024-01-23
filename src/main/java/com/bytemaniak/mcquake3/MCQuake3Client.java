package com.bytemaniak.mcquake3;

import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Screens;
import com.bytemaniak.mcquake3.registry.client.Keybindings;
import com.bytemaniak.mcquake3.registry.client.Renderers;
import com.bytemaniak.mcquake3.screen.JumppadScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MCQuake3Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Packets.registerClientPackets();

        HandledScreens.register(Screens.JUMPPAD_SCREEN_HANDLER, JumppadScreen::new);

        Renderers.registerRenderers();
        Keybindings.registerKeybinds();

        GeckoLibUtil.addDataTicket(Weapon.SPEED);
    }
}
