package com.bytemaniak.mcuake.registry;

import com.bytemaniak.mcuake.screen.JumppadScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class Screens {
    public static final ScreenHandlerType<JumppadScreenHandler> JUMPPAD_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(JumppadScreenHandler::new);

    public static void loadScreens() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier("mcuake", "jumppad_power"), JUMPPAD_SCREEN_HANDLER);
    }
}
