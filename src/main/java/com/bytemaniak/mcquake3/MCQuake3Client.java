package com.bytemaniak.mcquake3;

import com.bytemaniak.mcquake3.blocks.ammo.render.AmmoBoxRenderer;
import com.bytemaniak.mcquake3.entity.projectile.render.*;
import com.bytemaniak.mcquake3.gui.MCQuake3GuiRenderer;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.*;
import com.bytemaniak.mcquake3.render.TrailRenderer;
import com.bytemaniak.mcquake3.screen.JumppadScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.util.Identifier;
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
        EntityRendererRegistry.register(Entities.BFG10K_PROJECTILE, BFG10KProjectileRenderer::new);

        BlockEntityRendererRegistry.register(Blocks.MACHINEGUN_AMMO_BOX_ENTITY,
                context -> new AmmoBoxRenderer<>(new Identifier("mcquake3", "machinegun_ammo_box")));
        BlockEntityRendererRegistry.register(Blocks.SHOTGUN_AMMO_BOX_ENTITY,
                context -> new AmmoBoxRenderer<>(new Identifier("mcquake3", "shotgun_ammo_box")));
        BlockEntityRendererRegistry.register(Blocks.GRENADE_AMMO_BOX_ENTITY,
                context -> new AmmoBoxRenderer<>(new Identifier("mcquake3", "grenade_ammo_box")));
        BlockEntityRendererRegistry.register(Blocks.ROCKET_AMMO_BOX_ENTITY,
                context -> new AmmoBoxRenderer<>(new Identifier("mcquake3", "rocket_ammo_box")));
        BlockEntityRendererRegistry.register(Blocks.LIGHTNING_AMMO_BOX_ENTITY,
                context -> new AmmoBoxRenderer<>(new Identifier("mcquake3", "lightning_ammo_box")));
        BlockEntityRendererRegistry.register(Blocks.RAILGUN_AMMO_BOX_ENTITY,
                context -> new AmmoBoxRenderer<>(new Identifier("mcquake3", "railgun_ammo_box")));
        BlockEntityRendererRegistry.register(Blocks.PLASMAGUN_AMMO_BOX_ENTITY,
                context -> new AmmoBoxRenderer<>(new Identifier("mcquake3", "plasmagun_ammo_box")));
        BlockEntityRendererRegistry.register(Blocks.BFG_AMMO_BOX_ENTITY,
                context -> new AmmoBoxRenderer<>(new Identifier("mcquake3", "bfg_ammo_box")));

        trailRenderer = new TrailRenderer();
        WorldRenderEvents.END.register(trailRenderer);

        HudRenderCallback.EVENT.register(new MCQuake3GuiRenderer());

        Keybindings.registerKeybinds();

        GeckoLibUtil.addDataTicket(Weapon.SPEED);
    }
}
