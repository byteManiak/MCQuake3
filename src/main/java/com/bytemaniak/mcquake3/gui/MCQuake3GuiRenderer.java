package com.bytemaniak.mcquake3.gui;

import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.network.events.QuakeMatchState;
import com.bytemaniak.mcquake3.util.MiscUtils;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class MCQuake3GuiRenderer implements HudRenderCallback {
    public int frags = 0;
    public int highestFrags = 0;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        ClientPlayerEntity plr = MinecraftClient.getInstance().player;
        if (plr == null) return;

        QuakePlayer player = (QuakePlayer) MinecraftClient.getInstance().player;

        Window window = MinecraftClient.getInstance().getWindow();
        int x = window.getScaledWidth()/2;
        int y = window.getScaledHeight() - 10;

        int id = player.getCurrentQuakeWeaponId();
        if (id > 0) {
            int slotChar = '\uFFF0'+player.getCurrentQuakeWeaponId();
            MiscUtils.drawText(matrixStack, Character.toString((char)slotChar), x - 150, y - 16, 0x00FFFFFF);
            if (plr.getMainHandStack().getItem() instanceof Weapon weapon) {
                int weaponAmmo = 0;

                for (int i = 0; i < plr.getInventory().size(); i++) {
                    ItemStack currentStack = plr.getInventory().getStack(i);
                    if (currentStack.isOf(weapon.ammoType))
                        weaponAmmo += currentStack.getCount();
                }
                MiscUtils.drawText(matrixStack, String.valueOf(weaponAmmo), x - 130, y, 0x00FFFFFF);
            }
        }

        if (player.inQuakeArena()) {
            int playerHealth = (int)(plr.getHealth()*5);
            int healthColor = (playerHealth < 100) ?
                    (((int)(0xFF * (100-playerHealth)/100.f)  << 16) + ((int)(0xFF * playerHealth/100.f) << 8)) :
                    (((int)(0xFF * (200-playerHealth)/100.f)) << 8)  + ((int)(0xFF * (playerHealth-100)/100.f));
            MiscUtils.drawText(matrixStack, "\uFFF0", x - 20, y - 16, 0x00FFFFFF);
            MiscUtils.drawText(matrixStack, String.valueOf(playerHealth), x, y, healthColor);

            RenderSystem.setShaderTexture(0, plr.getSkinTexture());
            PlayerSkinDrawer.draw(matrixStack, x - 50, y-9, 16, false, false);

            int playerArmor = player.getEnergyShield();
            int armorColor = (playerArmor < 100) ?
                    (((int)(0xFF * (100-playerArmor)/100.f)  << 16) + ((int)(0xFF * playerArmor/100.f) << 8)) :
                    (((int)(0xFF * (200-playerArmor)/100.f)) << 8)  + ((int)(0xFF * (playerArmor-100)/100.f));
            MiscUtils.drawText(matrixStack, "\uFFF9", x + 105, y - 16, 0x00FFFFFF);
            MiscUtils.drawText(matrixStack, String.valueOf(playerArmor), x + 125, y, armorColor);

            int w = window.getScaledWidth() - 32;

            MiscUtils.drawText(matrixStack, String.valueOf(QuakeMatchState.FRAG_LIMIT), w - 60, y - 28, 0x00FFFFFF);

            if (frags < highestFrags) {
                DrawableHelper.fill(matrixStack, w - 34, y - 39, w + 17, y - 19, 0x88888888);
                MiscUtils.drawText(matrixStack, String.valueOf(highestFrags), w - 32, y - 28, 0x00FFFFFF);

                DrawableHelper.fill(matrixStack, w - 8, y - 39, w + 17, y - 19, 0x88FF0022);
                DrawableHelper.drawBorder(matrixStack, w - 8, y - 39, 25, 20, 0xFFFFDD22);
                MiscUtils.drawText(matrixStack, String.valueOf(frags), w - 6, y - 28, 0x00FFFFFF);
            } else {
                DrawableHelper.fill(matrixStack, w - 34, y - 39, w - 9, y - 19, 0x882200FF);
                DrawableHelper.drawBorder(matrixStack, w - 34, y - 39, 25, 20, 0xFFFFDD22);
                MiscUtils.drawText(matrixStack, String.valueOf(frags), w - 32, y - 28, 0x00FFFFFF);

                DrawableHelper.fill(matrixStack, w - 8, y - 39, w + 17, y - 19, 0x88888888);
                MiscUtils.drawText(matrixStack, String.valueOf(highestFrags), w - 6, y - 28, 0x00FFFFFF);
            }
        }
    }
}
