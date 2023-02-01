package com.bytemaniak.mcuake.gui;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

public class MCuakeGuiRenderer implements HudRenderCallback {
    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MCuakePlayer player = (MCuakePlayer) MinecraftClient.getInstance().player;
        Window window = MinecraftClient.getInstance().getWindow();
        int width = window.getScaledWidth();
        int height = window.getScaledHeight();
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int playerHealth = player.getQuakeHealth();
        int playerArmor = player.getQuakeArmor();

        textRenderer.drawWithShadow(matrixStack, "Health", width / 10.f, height - textRenderer.fontHeight * 3.f, 0x00FFFFFF);
        textRenderer.drawWithShadow(matrixStack, String.valueOf(playerHealth), width / 10.f, height - textRenderer.fontHeight * 2.f, 65280);

        textRenderer.drawWithShadow(matrixStack, "Armor", 9 * width / 10.f, height - textRenderer.fontHeight * 3.f, 0x00FFFFFF);
        textRenderer.drawWithShadow(matrixStack, String.valueOf(playerArmor), 9 * width / 10.f, height - textRenderer.fontHeight * 2.f, 65280);
    }
}
