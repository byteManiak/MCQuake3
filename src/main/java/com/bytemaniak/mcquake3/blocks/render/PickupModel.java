package com.bytemaniak.mcquake3.blocks.render;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class PickupModel<T extends PickupEntity> extends DefaultedEntityGeoModel<T> {
    public PickupModel(Identifier assetSubpath) {
        super(assetSubpath);
    }

    @Override
    public RenderLayer getRenderType(T animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucentCull(texture);
    }
}
