package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import com.bytemaniak.mcquake3.gui.SliderWidgetSettable;
import com.bytemaniak.mcquake3.registry.Packets;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class JumppadScreen extends HandledScreen<JumppadScreenHandler> {
    private SliderWidgetSettable powerAmount;
    private float power;

    private static final Identifier TEXTURE = new Identifier("mcquake3:textures/gui/settings.png");

    public JumppadScreen(JumppadScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.of("Jump Pad Boost Settings"));
        power = handler.power;
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, title, titleX, titleY, 4210752, false);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        context.drawTexture(TEXTURE, width/2 - 99, height/2 - 58, 0, 0, 198, 117);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        Optional<Element> hovered = hoveredElement(mouseX, mouseY);
        hovered.ifPresent(element -> element.mouseDragged(mouseX, mouseY, button, deltaX, deltaY));
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        int baseX = width/2 - 28;
        int baseY = height/2 - 20;

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY = backgroundHeight / 2 - 48;

        ButtonWidget updatePower = ButtonWidget.builder(Text.of("Apply"), (button) -> {
            // Request the server to update the jump pad's stats when pressing the apply button
            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeFloat(power);
            ClientPlayNetworking.send(Packets.JUMPPAD_UPDATE_POWER, msg);
        }).dimensions(baseX + 6, baseY + 52, 40, 20).build();

        ButtonWidget increment = ButtonWidget.builder(Text.of("+"), (button) -> {
            int val = (int) (power * 10) + 1;
            power = val / 10.f;
            powerAmount.setValue(power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX);
        }).dimensions(baseX + 97, baseY - 6, 20, 20).build();

        ButtonWidget decrement = ButtonWidget.builder(Text.of("-"), (button) -> {
            int val = (int) (power * 10) - 1;
            power = val / 10.f;
            powerAmount.setValue(power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX);
        }).dimensions(baseX - 11, baseY - 6, 20, 20).build();

        powerAmount = new SliderWidgetSettable(baseX + 11, baseY - 6, 85, 20, Text.of(String.format("%.2f", power)), power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX) {
            @Override
            protected void updateMessage() {
                setMessage(Text.of(String.format("%.2f", power)));
            }

            @Override
            protected void applyValue() { power = .5f + (float)value * JumppadEntity.JUMPPAD_ENTITY_POWER_MAX; }
        };

        TextWidget powerForwardText = new TextWidget(Text.of("Power:"), textRenderer);
        powerForwardText.setPosition(baseX - 39, baseY);
        powerForwardText.setWidth(0);
        addDrawableChild(powerForwardText);

        addDrawableChild(updatePower);
        addDrawableChild(increment);
        addDrawableChild(decrement);
        addDrawableChild(powerAmount);
    }
}
