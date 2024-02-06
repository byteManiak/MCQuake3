package com.bytemaniak.mcquake3.screen;

import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
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
    public void init() {
        backgroundWidth = 176;
        backgroundHeight = 193;

        super.init();
    }
}
