package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.entity.projectile.render.GrenadeRenderer;
import com.bytemaniak.mcuake.entity.projectile.render.PlasmaBallRenderer;
import com.bytemaniak.mcuake.entity.projectile.render.RocketRenderer;
import com.bytemaniak.mcuake.entity.projectile.render.ShellRenderer;
import com.bytemaniak.mcuake.gui.MCuakeGuiRenderer;
import com.bytemaniak.mcuake.items.Weapon;
import com.bytemaniak.mcuake.registry.Entities;
import com.bytemaniak.mcuake.registry.Keybindings;
import com.bytemaniak.mcuake.registry.Packets;
import com.bytemaniak.mcuake.registry.Screens;
import com.bytemaniak.mcuake.render.TrailRenderer;
import com.bytemaniak.mcuake.screen.JumppadScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MCuakeClient implements ClientModInitializer {
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

        HudRenderCallback.EVENT.register(new MCuakeGuiRenderer());

        Keybindings.registerKeybinds();

        GeckoLibUtil.addDataTicket(Weapon.SPEED);
    }
}
