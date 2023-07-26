package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.screen.JumppadScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class Screens {
    public static final ScreenHandlerType<JumppadScreenHandler> JUMPPAD_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(JumppadScreenHandler::new);

    public static void loadScreens() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier("mcquake3", "jumppad_power"), JUMPPAD_SCREEN_HANDLER);
    }
}
