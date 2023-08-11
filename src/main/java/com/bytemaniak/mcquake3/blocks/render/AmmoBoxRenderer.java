package com.bytemaniak.mcquake3.blocks.render;

import com.bytemaniak.mcquake3.blocks.AmmoBoxEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AmmoBoxRenderer extends GeoBlockRenderer<AmmoBoxEntity> {
    public AmmoBoxRenderer() {
        super(new DefaultedEntityGeoModel<AmmoBoxEntity>(new Identifier("mcquake3", "ammo_box")));
    }
}
