package com.bytemaniak.mcquake3.blocks.ammo.render;

import com.bytemaniak.mcquake3.blocks.ammo.AmmoBoxEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class AmmoBoxModel<T extends AmmoBoxEntity> extends DefaultedEntityGeoModel<T> {
    public AmmoBoxModel(Identifier assetSubpath) {
        super(assetSubpath);
    }

    @Override
    public RenderLayer getRenderType(T animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucentCull(texture);
    }
}
