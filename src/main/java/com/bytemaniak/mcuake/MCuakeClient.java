package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.screen.JumppadScreen;
import com.bytemaniak.mcuake.registry.Packets;
import com.bytemaniak.mcuake.entity.projectile.PlasmaBallRenderer;
import com.bytemaniak.mcuake.entity.projectile.ShellRenderer;
import com.bytemaniak.mcuake.gui.MCuakeGuiRenderer;
import com.bytemaniak.mcuake.registry.Entities;
import com.bytemaniak.mcuake.registry.Screens;
import com.bytemaniak.mcuake.render.TrailRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class MCuakeClient implements ClientModInitializer {
    public static TrailRenderer trailRenderer;

    @Override
    public void onInitializeClient() {
        Packets.registerClientPackets();

        HandledScreens.register(Screens.JUMPPAD_SCREEN_HANDLER, JumppadScreen::new);

        EntityRendererRegistry.register(Entities.PLASMA_BALL, PlasmaBallRenderer::new);
        EntityRendererRegistry.register(Entities.SHELL, ShellRenderer::new);

        trailRenderer = new TrailRenderer();
        WorldRenderEvents.END.register(trailRenderer);

        HudRenderCallback.EVENT.register(new MCuakeGuiRenderer());
    }
}
