package com.bytemaniak.mcuake.blocks;

import com.bytemaniak.mcuake.cs.CSMessages;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class JumppadScreen extends HandledScreen<JumppadScreenHandler> {
    private ButtonWidget updatePower;
    private SliderWidget powerForward, powerUp;
    private TextWidget powerForwardText, powerUpText;
    private float forward_power = 0, up_power = 0;

    private static final Identifier TEXTURE = new Identifier("mcuake", "textures/gui/settings.png");

    public JumppadScreen(JumppadScreenHandler handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, Text.of("Jump Pad Settings"));
        forward_power = handler.forward_power;
        up_power = handler.up_power;
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY)
    {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, width/2 - 99, height/2 - 58, 0, 0, 198, 117);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init()
    {
        super.init();

        int baseX = width/2 - 28;
        int baseY = height/2 - 20;

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY = backgroundHeight / 2 - 48;

        updatePower = ButtonWidget.builder(Text.of("Apply"), (button) -> {
            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeFloat(forward_power);
            msg.writeFloat(up_power);
            ClientPlayNetworking.send(CSMessages.JUMPPAD_UPDATE_POWER, msg);
        }).dimensions(baseX + 5, baseY + 44, 40, 20).build();

        powerForward = new SliderWidget(baseX + 17, baseY - 6, 100, 20, Text.of(String.format("%.2f", forward_power)), forward_power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.of(String.format("%.2f", forward_power)));
            }

            @Override
            protected void applyValue() {
                forward_power = (float)this.value * JumppadEntity.JUMPPAD_ENTITY_POWER_MAX;
            }
        };

        powerUp = new SliderWidget(baseX + 17, baseY + 16, 100, 20, Text.of(String.format("%.2f", up_power)), up_power / JumppadEntity.JUMPPAD_ENTITY_POWER_MAX) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.of(String.format("%.2f", up_power)));
            }

            @Override
            protected void applyValue() {
                up_power = (float)this.value * JumppadEntity.JUMPPAD_ENTITY_POWER_MAX;
            }
        };

        addDrawable(updatePower);
        addDrawable(powerForward);
        addDrawable(powerUp);
        addSelectableChild(updatePower);
        addSelectableChild(powerForward);
        addSelectableChild(powerUp);

        powerForwardText = new TextWidget(Text.of("Forward boost:"), textRenderer);
        powerForwardText.setPos(baseX - 25, baseY);
        powerForwardText.setWidth(0);
        powerUpText = new TextWidget(Text.of("Upward boost:"), textRenderer);
        powerUpText.setPos(baseX - 22, baseY + 22);
        powerUpText.setWidth(0);
        addDrawable(powerForwardText);
        addDrawable(powerUpText);
    }
}
