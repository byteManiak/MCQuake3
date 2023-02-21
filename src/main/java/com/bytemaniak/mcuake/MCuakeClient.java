package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.blocks.jumppad.JumppadScreen;
import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.entity.projectile.PlasmaBallRenderer;
import com.bytemaniak.mcuake.entity.projectile.ShellRenderer;
import com.bytemaniak.mcuake.gui.MCuakeGuiRenderer;
import com.bytemaniak.mcuake.render.RailRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class MCuakeClient implements ClientModInitializer {
    public static RailRenderer railRenderer;

    @Override
    public void onInitializeClient() {
        CSMessages.registerClientPackets();

        HandledScreens.register(MCuake.JUMPPAD_SCREEN_HANDLER, JumppadScreen::new);

        EntityRendererRegistry.register(MCuake.PLASMA_BALL, PlasmaBallRenderer::new);
        EntityRendererRegistry.register(MCuake.SHELL, ShellRenderer::new);

        railRenderer = new RailRenderer();
        WorldRenderEvents.AFTER_TRANSLUCENT.register(railRenderer);

        HudRenderCallback.EVENT.register(new MCuakeGuiRenderer());
    }
}
