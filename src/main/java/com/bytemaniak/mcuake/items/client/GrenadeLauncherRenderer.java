package com.bytemaniak.mcuake.items.client;

import com.bytemaniak.mcuake.items.GrenadeLauncher;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GrenadeLauncherRenderer extends GeoItemRenderer<GrenadeLauncher> {
    public GrenadeLauncherRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier("mcuake", "grenade_launcher")));
    }
}
