package com.bytemaniak.mcuake.blocks.jumppad;

import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.gui.SliderWidgetSettable;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class JumppadScreen extends HandledScreen<JumppadScreenHandler> {
    private ButtonWidget updatePower;
    private ButtonWidget incrementForward, decrementForward;
    private ButtonWidget incrementUp, decrementUp;
    private SliderWidgetSettable powerForward, powerUp;
    private TextWidget powerForwardText, powerUpText;
    private float forward_power = 0, up_power = 0;

    private static final Identifier TEXTURE = new Identifier("mcuake", "textures/gui/settings.png");

    public JumppadScreen(JumppadScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.of("Jump Pad Boost Settings"));
        forward_power = handler.forward_power;
        up_power = handler.up_power;
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, width/2 - 99, height/2 - 58, 0, 0, 198, 117);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        Optional<Element> hovered = hoveredElement(mouseX, mouseY);
        if (hovered.isPresent()) hovered.get().mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        int baseX = width/2 - 28;
        int baseY = height/2 - 20;

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY = backgroundHeight / 2 - 48;

        updatePower = ButtonWidget.builder(Text.of("Apply"), (button) -> {
            // Request the server to update the jump pad's stats when pressing the apply button
            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeFloat(forward_power);
            msg.writeFloat(up_power);
            ClientPlayNetworking.send(CSMessages.JUMPPAD_UPDATE_POWER, msg);
        }).dimensions(baseX + 6, baseY + 52, 40, 20).build();

        incrementForward = ButtonWidget.builder(Text.of("+"), (button) -> {
            int val = (int)(forward_power * 10) + 1;
            forward_power = val / 10.f;
            powerForward.setValue(forward_power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX);
        }).dimensions(baseX + 97, baseY - 6, 20, 20).build();

        decrementForward = ButtonWidget.builder(Text.of("-"), (button) -> {
            int val = (int)(forward_power * 10) - 1;
            forward_power = val / 10.f;
            powerForward.setValue(forward_power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX);
        }).dimensions(baseX - 11, baseY - 6, 20, 20).build();

        incrementUp = ButtonWidget.builder(Text.of("+"), (button) -> {
            int val = (int)(up_power * 10) + 1;
            up_power = val / 10.f;
            powerUp.setValue(up_power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX);
        }).dimensions(baseX + 97, baseY + 20, 20, 20).build();

        decrementUp = ButtonWidget.builder(Text.of("-"), (button) -> {
            int val = (int)(up_power * 10) - 1;
            up_power = val / 10.f;
            powerUp.setValue(up_power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX);
        }).dimensions(baseX - 11, baseY + 20, 20, 20).build();

        powerForward = new SliderWidgetSettable(baseX + 11, baseY - 6, 85, 20, Text.of(String.format("%.2f", forward_power)), forward_power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.of(String.format("%.2f", forward_power)));
            }

            @Override
            protected void applyValue() {
                forward_power = (float)this.value * JumppadEntity.JUMPPAD_ENTITY_POWER_MAX;
            }
        };

        powerUp = new SliderWidgetSettable(baseX + 11, baseY + 20, 85, 20, Text.of(String.format("%.2f", up_power)), up_power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.of(String.format("%.2f", up_power)));
            }

            @Override
            protected void applyValue() {
                up_power = (float)this.value * JumppadEntity.JUMPPAD_ENTITY_POWER_MAX;
            }
        };

        powerForwardText = new TextWidget(Text.of("Forward:"), textRenderer);
        powerForwardText.setPos(baseX - 39, baseY);
        powerForwardText.setWidth(0);
        powerUpText = new TextWidget(Text.of("Upward:"), textRenderer);
        powerUpText.setPos(baseX - 35, baseY + 22);
        powerUpText.setWidth(0);
        addDrawable(powerForwardText);
        addDrawable(powerUpText);

        addDrawable(updatePower);
        addDrawable(incrementForward);
        addDrawable(decrementForward);
        addDrawable(powerForward);
        addDrawable(incrementUp);
        addDrawable(decrementUp);
        addDrawable(powerUp);

        addSelectableChild(updatePower);
        addSelectableChild(incrementForward);
        addSelectableChild(decrementForward);
        addSelectableChild(powerForward);
        addSelectableChild(incrementUp);
        addSelectableChild(decrementUp);
        addSelectableChild(powerUp);
    }
}
