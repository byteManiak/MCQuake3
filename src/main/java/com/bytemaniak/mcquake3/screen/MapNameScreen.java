package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.registry.Packets;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MapNameScreen extends HandledScreen<MapNameScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("mcquake3:textures/gui/settings.png");

    public MapNameScreen(MapNameScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
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
    protected void init() {
        super.init();

        int baseX = width/2 - 28;
        int baseY = height/2 - 20;

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY = backgroundHeight / 2 - 48;

        TextWidget mapNameText = new TextWidget(Text.of("Map name:"), textRenderer);
        mapNameText.setPosition(baseX - 39, baseY + 6);
        mapNameText.setWidth(0);

        TextFieldWidget mapName = new TextFieldWidget(textRenderer, baseX-8, baseY, 116, 20, Text.empty());

        ButtonWidget selectMap = ButtonWidget.builder(Text.of("Start"), (button) -> {
            if (mapName.getText().isEmpty()) return;

            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeString(mapName.getText());
            msg.writeBoolean(false);
            ClientPlayNetworking.send(Packets.MAP_SELECT_DELETE, msg);
            MapNameScreen.this.close();
        }).dimensions(baseX - 20, baseY + 52, 40, 20).build();

        ButtonWidget deleteMap = ButtonWidget.builder(Text.of("Delete"), (button) -> {
            if (mapName.getText().isEmpty()) return;

            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeString(mapName.getText());
            msg.writeBoolean(true);
            ClientPlayNetworking.send(Packets.MAP_SELECT_DELETE, msg);
            MapNameScreen.this.close();
        }).dimensions(baseX + 24, baseY + 52, 40, 20).build();

        addDrawableChild(mapNameText);
        addDrawableChild(mapName);
        addDrawableChild(selectMap);
        addDrawableChild(deleteMap);
    }
}
