package com.bytemaniak.mcuake.entity.projectile.render;

import com.bytemaniak.mcuake.entity.projectile.Grenade;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class GrenadeRenderer extends EntityRenderer<Grenade> {
    private static final Identifier TEXTURE = new Identifier("mcuake", "textures/entity/grenade.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityTranslucentEmissive(TEXTURE, false);

    public GrenadeRenderer(EntityRendererFactory.Context ctx) { super(ctx); }

    @Override
    public Identifier getTexture(Grenade entity) { return TEXTURE; }
}
