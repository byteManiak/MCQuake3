package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.screen.PlayerSettingsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PlayerSettings extends Item {
    public PlayerSettings() { super(new Item.Settings().maxCount(1)); }

    @Environment(EnvType.CLIENT)
    public void openSettingsScreen(PlayerEntity user) {
        if (user.getUuid() == MinecraftClient.getInstance().player.getUuid()) {
            MinecraftClient.getInstance().setScreen(new PlayerSettingsScreen(Text.of("Bruh"), user));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) openSettingsScreen(user);
        return super.use(world, user, hand);
    }
}
