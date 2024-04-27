package com.bytemaniak.mcquake3.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlasmaInducerScreen extends AbstractFurnaceScreen<PlasmaInducerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("mcquake3:textures/gui/plasma_inducer.png");

    public PlasmaInducerScreen(PlasmaInducerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new PlasmaInducerRecipeBookScreen(), inventory, Text.of("Plasma Inducer"), TEXTURE);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int k;
        RenderSystem.setShaderTexture(0, TEXTURE);
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (handler.isBurning()) {
            k = handler.getFuelProgress();
            context.drawTexture(TEXTURE, x + 65, y + 66 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        k = handler.getCookProgress();
        context.drawTexture(TEXTURE, x + 88, y + 64, 176, 14, k + 1, 16);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, title, titleX, titleY, 4210752, false);
    }

    @Override
    public void init() {
        backgroundWidth = 176;
        backgroundHeight = 193;

        super.init();
    }
}
