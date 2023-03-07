package com.bytemaniak.mcuake.items.client;

import com.bytemaniak.mcuake.items.Shotgun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ShotgunRenderer extends GeoItemRenderer<Shotgun> {
    public ShotgunRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier("mcuake", "shotgun")));
    }
}
