package com.bytemaniak.mcquake3.gui;

import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;

public class MCQuake3GuiRenderer implements HudRenderCallback {
    private static final Identifier QUAKE_FONT = new Identifier("mcquake3:quake_hud");

    private void drawText(DrawContext context, String str, int x, int y, int color) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        LiteralTextContent textContent = new LiteralTextContent(str);
        MutableText text = MutableText.of(textContent).setStyle(Style.EMPTY.withFont(QUAKE_FONT));

        context.drawText(textRenderer, text, x, y - textRenderer.fontHeight, color, true);
    }

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        ClientPlayerEntity plr = MinecraftClient.getInstance().player;
        if (plr == null) return;

        QuakePlayer player = (QuakePlayer) MinecraftClient.getInstance().player;

        Window window = MinecraftClient.getInstance().getWindow();
        int x = window.getScaledWidth()/2;
        int y = window.getScaledHeight() - 10;

        int id = player.getCurrentQuakeWeaponId();
        if (id > 0) {
            int slotChar = '\uFFF0'+player.getCurrentQuakeWeaponId();
            drawText(context, Character.toString((char)slotChar), x - 200, y - 16, 0x00FFFFFF);
            if (plr.getMainHandStack().getItem() instanceof Weapon weapon) {
                int weaponAmmo = 0;

                for (int i = 0; i < plr.getInventory().size(); i++) {
                    ItemStack currentStack = plr.getInventory().getStack(i);
                    if (currentStack.isOf(weapon.ammoType)) {
                        weaponAmmo += currentStack.getCount();
                    }
                }
                drawText(context, String.valueOf(weaponAmmo), x - 180, y, 0x00FFFFFF);
            }
        }

        if (player.quakeGuiEnabled()) {
            int playerHealth = (int)(plr.getHealth()*5);
            int healthColor = (playerHealth < 100) ?
                    (((int)(0xFF * (100-playerHealth)/100.f)  << 16) + ((int)(0xFF * playerHealth/100.f) << 8)) :
                    (((int)(0xFF * (200-playerHealth)/100.f)) << 8)  + ((int)(0xFF * (playerHealth-100)/100.f));
            drawText(context, "\uFFF0", x - 150, y - 16, 0x00FFFFFF);
            drawText(context, String.valueOf(playerHealth), x - 130, y, healthColor);

            int playerArmor = player.getEnergyShield();
            int armorColor = (playerArmor < 100) ?
                    (((int)(0xFF * (100-playerArmor)/100.f)  << 16) + ((int)(0xFF * playerArmor/100.f) << 8)) :
                    (((int)(0xFF * (200-playerArmor)/100.f)) << 8)  + ((int)(0xFF * (playerArmor-100)/100.f));
            drawText(context, "\uFFF9", x + 105, y - 16, 0x00FFFFFF);
            drawText(context, String.valueOf(playerArmor), x + 125, y, armorColor);
        }
    }
}
