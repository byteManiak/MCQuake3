package com.bytemaniak.mcquake3.registry.client;

import com.bytemaniak.mcquake3.registry.Packets;
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

    private static KeyBinding registerKeybind(String id, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcquake3."+id, InputUtil.Type.KEYSYM, key,
                "category.mcquake3.bindings"));
    }

    public static void registerKeybinds() {
        playerSettingsMenu = registerKeybind("playermenu", GLFW.GLFW_KEY_O);
        playerTaunt = registerKeybind("playertaunt", GLFW.GLFW_KEY_Z);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (playerSettingsMenu.wasPressed())
                client.setScreen(new PlayerSettingsScreen(Text.of("MCQuake3 Player Settings"), client.player));
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (playerTaunt.wasPressed())
                ClientPlayNetworking.send(Packets.PLAYER_TAUNT, PacketByteBufs.empty());
        });
    }
}
