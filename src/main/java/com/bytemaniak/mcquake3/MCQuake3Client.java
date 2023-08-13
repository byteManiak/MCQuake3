package com.bytemaniak.mcquake3;

import com.bytemaniak.mcquake3.blocks.render.PickupRenderer;
import com.bytemaniak.mcquake3.entity.projectile.render.*;
import com.bytemaniak.mcquake3.gui.MCQuake3GuiRenderer;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.*;
import com.bytemaniak.mcquake3.render.TrailRenderer;
import com.bytemaniak.mcquake3.screen.JumppadScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
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
                context -> new PickupRenderer<>(Blocks.MACHINEGUN_AMMO_BOX));
        BlockEntityRendererRegistry.register(Blocks.SHOTGUN_AMMO_BOX_ENTITY,
                context -> new PickupRenderer<>(Blocks.SHOTGUN_AMMO_BOX));
        BlockEntityRendererRegistry.register(Blocks.GRENADE_AMMO_BOX_ENTITY,
                context -> new PickupRenderer<>(Blocks.GRENADE_AMMO_BOX));
        BlockEntityRendererRegistry.register(Blocks.ROCKET_AMMO_BOX_ENTITY,
                context -> new PickupRenderer<>(Blocks.ROCKET_AMMO_BOX));
        BlockEntityRendererRegistry.register(Blocks.LIGHTNING_AMMO_BOX_ENTITY,
                context -> new PickupRenderer<>(Blocks.LIGHTNING_AMMO_BOX));
        BlockEntityRendererRegistry.register(Blocks.RAILGUN_AMMO_BOX_ENTITY,
                context -> new PickupRenderer<>(Blocks.RAILGUN_AMMO_BOX));
        BlockEntityRendererRegistry.register(Blocks.PLASMAGUN_AMMO_BOX_ENTITY,
                context -> new PickupRenderer<>(Blocks.PLASMAGUN_AMMO_BOX));
        BlockEntityRendererRegistry.register(Blocks.BFG_AMMO_BOX_ENTITY,
                context -> new PickupRenderer<>(Blocks.BFG_AMMO_BOX));
        BlockEntityRendererRegistry.register(Blocks.HEALTH5_ENTITY,
                context -> new PickupRenderer<>(Blocks.HEALTH5));
        BlockEntityRendererRegistry.register(Blocks.HEALTH25_ENTITY,
                context -> new PickupRenderer<>(Blocks.HEALTH25));
        BlockEntityRendererRegistry.register(Blocks.HEALTH50_ENTITY,
                context -> new PickupRenderer<>(Blocks.HEALTH50));

        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.HEALTH5_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.HEALTH25_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.HEALTH50_BLOCK, RenderLayer.getTranslucent());

        trailRenderer = new TrailRenderer();
        WorldRenderEvents.END.register(trailRenderer);

        HudRenderCallback.EVENT.register(new MCQuake3GuiRenderer());

        Keybindings.registerKeybinds();

        GeckoLibUtil.addDataTicket(Weapon.SPEED);
    }
}
