package com.bytemaniak.mcquake3;

import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.ClientEvents;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Screens;
import com.bytemaniak.mcquake3.registry.client.Keybindings;
import com.bytemaniak.mcquake3.registry.client.Renderers;
import net.fabricmc.api.ClientModInitializer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MCQuake3Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Packets.registerClientPackets();

        Screens.registerScreens();
        Renderers.registerRenderers();
        Keybindings.registerKeybinds();

        ClientEvents.registerEvents();

        GeckoLibUtil.addDataTicket(Weapon.SPEED);
    }
}
