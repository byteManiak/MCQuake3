package com.bytemaniak.mcuake.registry;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybindings {
    private static KeyBinding playerSettingsMenu;

    public static void registerKeybinds() {
        playerSettingsMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcuake.playermenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.mcuake.bindings"));
    }
}
