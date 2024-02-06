package com.bytemaniak.mcquake3.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlasmaInducerScreen extends AbstractFurnaceScreen<PlasmaInducerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("mcquake3:textures/gui/plasma_inducer.png");

    public PlasmaInducerScreen(PlasmaInducerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new PlasmaInducerRecipeBookScreen(), inventory, Text.of("Plasma Inducer"), TEXTURE);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        textRenderer.draw(matrices, title, (float)titleX, (float)titleY, 4210752);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, width/2 - 88, height/2 - 96, 0, 0, 176, 193);
    }

    @Override
    public void init() {
        super.init();

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY = -8;
    }
}
