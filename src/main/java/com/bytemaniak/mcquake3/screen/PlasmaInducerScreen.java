package com.bytemaniak.mcquake3.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlasmaInducerScreen extends AbstractFurnaceScreen<PlasmaInducerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("mcquake3:textures/gui/plasma_inducer.png");
    private boolean narrow;

    public PlasmaInducerScreen(PlasmaInducerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new PlasmaInducerRecipeBookScreen(), inventory, Text.of("Plasma Inducer"), TEXTURE);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        int k;
        RenderSystem.setShaderTexture(0, TEXTURE);
        AbstractFurnaceScreen.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (handler.isBurning()) {
            k = handler.getFuelProgress();
            AbstractFurnaceScreen.drawTexture(matrices, x + 65, y + 66 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        k = handler.getCookProgress();
        AbstractFurnaceScreen.drawTexture(matrices, x + 88, y + 64, 176, 14, k + 1, 16);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        textRenderer.draw(matrices, title, (float)titleX, (float)titleY, 4210752);
    }

    @Override
    public void init() {
        backgroundWidth = 176;
        backgroundHeight = 193;

        narrow = width < 379;
        super.init();
    }
}
