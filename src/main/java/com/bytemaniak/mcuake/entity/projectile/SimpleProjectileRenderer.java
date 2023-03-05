package com.bytemaniak.mcuake.entity.projectile;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class SimpleProjectileRenderer extends EntityRenderer<SimpleProjectile> {
    private static final Identifier TEXTURE = new Identifier("mcuake", "none");

    public SimpleProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SimpleProjectile entity) {
        return TEXTURE;
    }
}
