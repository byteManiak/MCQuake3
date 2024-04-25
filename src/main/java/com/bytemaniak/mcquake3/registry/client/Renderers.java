package com.bytemaniak.mcquake3.registry.client;

import com.bytemaniak.mcquake3.blocks.render.PickupRenderer;
import com.bytemaniak.mcquake3.entity.projectile.render.*;
import com.bytemaniak.mcquake3.gui.FeedbackManager;
import com.bytemaniak.mcquake3.gui.MCQuake3GuiRenderer;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Entities;
import com.bytemaniak.mcquake3.render.QuadDamageGlintRenderer;
import com.bytemaniak.mcquake3.render.TrailRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;

public class Renderers {
    public static MCQuake3GuiRenderer hudRenderer = new MCQuake3GuiRenderer();
    public static FeedbackManager feedbacks = new FeedbackManager();
    public static TrailRenderer trailRenderer;

    private static void registerEntityRenderers() {
        EntityRendererRegistry.register(Entities.PLASMA_BALL, PlasmaBallRenderer::new);
        EntityRendererRegistry.register(Entities.SHELL, ShellRenderer::new);
        EntityRendererRegistry.register(Entities.GRENADE, GrenadeRenderer::new);
        EntityRendererRegistry.register(Entities.ROCKET, RocketRenderer::new);
        EntityRendererRegistry.register(Entities.BFG10K_PROJECTILE, BFG10KProjectileRenderer::new);
    }

    private static void registerBlockRenderers() {
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
        BlockEntityRendererRegistry.register(Blocks.HASTE_ENTITY,
                context -> new PickupRenderer<>(Blocks.HASTE));
        BlockEntityRendererRegistry.register(Blocks.INVISIBILITY_ENTITY,
                context -> new PickupRenderer<>(Blocks.INVISIBILITY));
        BlockEntityRendererRegistry.register(Blocks.QUAD_DAMAGE_ENTITY,
                context -> new PickupRenderer<>(Blocks.QUAD_DAMAGE));
        BlockEntityRendererRegistry.register(Blocks.HEALTH5_ENTITY,
                context -> new PickupRenderer<>(Blocks.HEALTH5));
        BlockEntityRendererRegistry.register(Blocks.HEALTH25_ENTITY,
                context -> new PickupRenderer<>(Blocks.HEALTH25));
        BlockEntityRendererRegistry.register(Blocks.HEALTH50_ENTITY,
                context -> new PickupRenderer<>(Blocks.HEALTH50));
        BlockEntityRendererRegistry.register(Blocks.SHIELD_CELL_ENTITY,
                context -> new PickupRenderer<>(Blocks.SHIELD_CELL));
        BlockEntityRendererRegistry.register(Blocks.LIGHT_ENERGY_SHIELD_ENTITY,
                context -> new PickupRenderer<>(Blocks.LIGHT_ENERGY_SHIELD));
        BlockEntityRendererRegistry.register(Blocks.HEAVY_ENERGY_SHIELD_ENTITY,
                context -> new PickupRenderer<>(Blocks.HEAVY_ENERGY_SHIELD));
        BlockEntityRendererRegistry.register(Blocks.MACHINEGUN_ENTITY,
                context -> new PickupRenderer<>(Blocks.MACHINEGUN));
        BlockEntityRendererRegistry.register(Blocks.SHOTGUN_ENTITY,
                context -> new PickupRenderer<>(Blocks.SHOTGUN));
        BlockEntityRendererRegistry.register(Blocks.GRENADE_ENTITY,
                context -> new PickupRenderer<>(Blocks.GRENADE));
        BlockEntityRendererRegistry.register(Blocks.ROCKET_ENTITY,
                context -> new PickupRenderer<>(Blocks.ROCKET));
        BlockEntityRendererRegistry.register(Blocks.LIGHTNING_ENTITY,
                context -> new PickupRenderer<>(Blocks.LIGHTNING));
        BlockEntityRendererRegistry.register(Blocks.RAILGUN_ENTITY,
                context -> new PickupRenderer<>(Blocks.RAILGUN));
        BlockEntityRendererRegistry.register(Blocks.PLASMAGUN_ENTITY,
                context -> new PickupRenderer<>(Blocks.PLASMAGUN));
        BlockEntityRendererRegistry.register(Blocks.BFG_ENTITY,
                context -> new PickupRenderer<>(Blocks.BFG));

        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.HEALTH5_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.HEALTH25_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.HEALTH50_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.SHIELD_CELL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.INVISIBILITY_BLOCK, RenderLayer.getTranslucent());
    }

    private static void registerFeatureRenderers() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerEntityRenderer) {
                registrationHelper.register(
                        new QuadDamageGlintRenderer(
                                entityRenderer,
                                context.getModelLoader()
                        )
                );
            }
        });
    }

    public static void registerRenderers() {
        registerEntityRenderers();
        registerBlockRenderers();
        registerFeatureRenderers();

        trailRenderer = new TrailRenderer();
        WorldRenderEvents.END.register(trailRenderer);

        HudRenderCallback.EVENT.register(hudRenderer);
        HudRenderCallback.EVENT.register(feedbacks);
    }
}
