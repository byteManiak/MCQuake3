package com.bytemaniak.mcuake.registry;

import com.bytemaniak.mcuake.screen.PlayerSettingsScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class Keybindings {
    private static KeyBinding playerSettingsMenu;

    public static void registerKeybinds() {
        playerSettingsMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcuake.playermenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.mcuake.bindings"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (playerSettingsMenu.wasPressed()) {
                client.setScreen(new PlayerSettingsScreen(Text.of("MCuake Player Settings"), client.player));
            }
        });
    }
}
