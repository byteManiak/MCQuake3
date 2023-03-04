package com.bytemaniak.mcuake.items.client;

import com.bytemaniak.mcuake.items.Gauntlet;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GauntletRenderer extends GeoItemRenderer<Gauntlet> {
    public GauntletRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier("mcuake", "gauntlet")));
    }
}
