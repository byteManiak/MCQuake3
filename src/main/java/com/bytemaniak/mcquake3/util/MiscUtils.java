package com.bytemaniak.mcquake3.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class MiscUtils {
    public static float toMCDamage(float amount) {
        return amount/5;
    }
    public static float fromMCDamage(float amount) { return amount*5; }
    public static int toTicks(float seconds) { return (int)(seconds * 20); }

    private static final Identifier QUAKE_FONT = new Identifier("mcquake3:quake_hud");

    public static void drawText(DrawContext context, String str, int x, int y, int color) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        MutableText text = Text.literal(str).setStyle(Style.EMPTY.withFont(QUAKE_FONT));
        context.drawTextWithShadow(textRenderer, text, x, y - textRenderer.fontHeight, color);
    }

    public static void insertInNonHotbarInventory(ItemStack stack, PlayerInventory inventory) {
        DefaultedList<ItemStack> main = inventory.main;

        for (int i = PlayerInventory.getHotbarSize(); i < main.size(); i++) {
            if (stack.getCount() == 0) break;

            if (main.get(i).isOf(stack.getItem()))
                stack.setCount(inventory.addStack(i, stack));
            else if (main.get(i).isEmpty()) {
                inventory.insertStack(i, stack);
            }
        }
    }
}
