package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.screen.JumppadScreen;
import com.bytemaniak.mcquake3.screen.JumppadScreenHandler;
import com.bytemaniak.mcquake3.screen.PlasmaInducerScreen;
import com.bytemaniak.mcquake3.screen.PlasmaInducerScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class Screens {
    public static final ScreenHandlerType<PlasmaInducerScreenHandler> PLASMA_INDUCER_SCREEN_HANDLER =
            new ScreenHandlerType<>(PlasmaInducerScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    public static final ScreenHandlerType<JumppadScreenHandler> JUMPPAD_SCREEN_HANDLER =
            new ExtendedScreenHandlerType<>(JumppadScreenHandler::new);

    public static void registerScreenHandlers() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier("mcquake3:plasma_inducer"), PLASMA_INDUCER_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier("mcquake3:jumppad_power"), JUMPPAD_SCREEN_HANDLER);
    }

    @Environment(EnvType.CLIENT)
    public static void registerScreens() {
        HandledScreens.register(PLASMA_INDUCER_SCREEN_HANDLER, PlasmaInducerScreen::new);
        HandledScreens.register(JUMPPAD_SCREEN_HANDLER, JumppadScreen::new);
    }
}
