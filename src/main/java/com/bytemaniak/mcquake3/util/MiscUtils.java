package com.bytemaniak.mcquake3.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class MiscUtils {
    public static float toMCDamage(float amount) {
        return amount/5;
    }
    public static float fromMCDamage(float amount) { return amount*5; }
    public static int toTicks(float seconds) { return (int)(seconds * 20); }

    private static final Identifier QUAKE_FONT = new Identifier("mcquake3:quake_hud");
    public static void drawText(MatrixStack matrixStack, String str, float x, float y, int color) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        LiteralTextContent textContent = new LiteralTextContent(str);
        MutableText text = MutableText.of(textContent).setStyle(Style.EMPTY.withFont(QUAKE_FONT));

        textRenderer.drawWithShadow(matrixStack, text, x, y - textRenderer.fontHeight, color);
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

    public static int getCountOfItemType(PlayerInventory inventory, Item item) {
        DefaultedList<ItemStack> main = inventory.main;
        int count = 0;

        for (int i = PlayerInventory.getHotbarSize(); i < main.size(); i++) {
            ItemStack stack = main.get(i);
            if (stack.isEmpty()) continue;
            if (stack.isOf(item)) count += stack.getCount();
        }

        return count;
    }
}
