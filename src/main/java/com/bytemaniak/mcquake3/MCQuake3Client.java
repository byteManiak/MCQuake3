package com.bytemaniak.mcquake3;

import com.bytemaniak.mcquake3.entity.projectile.render.GrenadeRenderer;
import com.bytemaniak.mcquake3.entity.projectile.render.PlasmaBallRenderer;
import com.bytemaniak.mcquake3.entity.projectile.render.RocketRenderer;
import com.bytemaniak.mcquake3.entity.projectile.render.ShellRenderer;
import com.bytemaniak.mcquake3.gui.MCQuake3GuiRenderer;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.Entities;
import com.bytemaniak.mcquake3.registry.Keybindings;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Screens;
import com.bytemaniak.mcquake3.render.TrailRenderer;
import com.bytemaniak.mcquake3.screen.JumppadScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MCQuake3Client implements ClientModInitializer {
    public static TrailRenderer trailRenderer;

    @Override
    public void onInitializeClient() {
        Packets.registerClientPackets();

        HandledScreens.register(Screens.JUMPPAD_SCREEN_HANDLER, JumppadScreen::new);

        EntityRendererRegistry.register(Entities.PLASMA_BALL, PlasmaBallRenderer::new);
        EntityRendererRegistry.register(Entities.SHELL, ShellRenderer::new);
        EntityRendererRegistry.register(Entities.GRENADE, GrenadeRenderer::new);
        EntityRendererRegistry.register(Entities.ROCKET, RocketRenderer::new);

        trailRenderer = new TrailRenderer();
        WorldRenderEvents.END.register(trailRenderer);

        HudRenderCallback.EVENT.register(new MCQuake3GuiRenderer());

        Keybindings.registerKeybinds();

        GeckoLibUtil.addDataTicket(Weapon.SPEED);
    }
}
