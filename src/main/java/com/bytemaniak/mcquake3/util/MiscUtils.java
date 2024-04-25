package com.bytemaniak.mcquake3.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;

public class MiscUtils {
    public static float toMCDamage(float amount) {
        return amount/5;
    }
    public static float fromMCDamage(float amount) { return amount*5; }
    public static int toTicks(float seconds) { return (int)(seconds * 20); }

    private static final Identifier QUAKE_FONT = new Identifier("mcquake3:quake_hud");
    public static void drawText(DrawContext context, String str, int x, int y, int color) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        LiteralTextContent textContent = new LiteralTextContent(str);
        MutableText text = MutableText.of(textContent).setStyle(Style.EMPTY.withFont(QUAKE_FONT));

        context.drawTextWithShadow(textRenderer, text, x, y - textRenderer.fontHeight, color);
    }
}
