package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.screen.PlayerSettingsScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class Keybindings {
    private static KeyBinding playerSettingsMenu;
    private static KeyBinding playerTaunt;

    public static void registerKeybinds() {
        playerSettingsMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcquake3.playermenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "category.mcquake3.bindings"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (playerSettingsMenu.wasPressed()) {
                client.setScreen(new PlayerSettingsScreen(Text.of("MCQuake3 Player Settings"), client.player));
            }
        });

        playerTaunt = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcquake3.playertaunt",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "category.mcquake3.bindings"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (playerTaunt.wasPressed()) {
                ClientPlayNetworking.send(Packets.PLAYER_TAUNT, PacketByteBufs.empty());
            }
        });
    }
}
