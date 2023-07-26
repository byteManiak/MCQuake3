package com.bytemaniak.mcuake.gui;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;

public class MCuakeGuiRenderer implements HudRenderCallback {
    private static final Identifier QUAKE_FONT = new Identifier("mcuake", "bebasneue");

    private void drawText(MatrixStack matrixStack, String str, float x, float y, int color) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        LiteralTextContent textContent = new LiteralTextContent(str);
        MutableText text = MutableText.of(textContent).setStyle(Style.EMPTY.withFont(QUAKE_FONT));

        textRenderer.drawWithShadow(matrixStack, text, x, y - textRenderer.fontHeight, color);
    }
    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        QuakePlayer player = (QuakePlayer) MinecraftClient.getInstance().player;

        if (player.isInQuakeMode()) {
            Window window = MinecraftClient.getInstance().getWindow();
            int x = window.getScaledWidth()/2;
            int y = window.getScaledHeight() - 10;
            int playerHealth = player.getQuakeHealth();
            int playerArmor = player.getQuakeArmor();
            int healthColor = (playerHealth < 100) ?
                    (((int)(0xFF * (100-playerHealth)/100.f)  << 16) + ((int)(0xFF * playerHealth/100.f) << 8)) :
                    (((int)(0xFF * (200-playerHealth)/100.f)) << 8)  + ((int)(0xFF * (playerHealth-100)/100.f));
            drawText(matrixStack, String.valueOf(playerHealth), x - 130, y, healthColor);
            drawText(matrixStack, String.valueOf(playerArmor), x + 105, y, 65280);

            drawText(matrixStack, player.getCurrentWeapon().toString(), x - 170, y - 16, 0x00FFFFFF);
            drawText(matrixStack, String.valueOf(player.getCurrentAmmo()), x - 170, y, 0x00FFFFFF);
        }
    }
}
