package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.registry.Packets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class MapBrowserScreen extends Screen {
    public MapBrowserScreen(Text title) {
        super(title);
    }

    public class MapList extends AlwaysSelectedEntryListWidget<MapList.MapEntry> {
        public MapList(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight, int x) {
            super(client, width, height, top, bottom, itemHeight);
            this.left = x;
            this.right = x + width;
            this.top = top;
            this.bottom = top + height;
            setRenderHorizontalShadows(false);
        }

        @Override
        public void appendNarrations(NarrationMessageBuilder builder) {}

        public void addMapEntry(String mapName) {
            MapEntry entry = new MapEntry(mapName);
            addEntry(entry);
            setSelected(entry);
        }

        public void clearList() { clearEntries(); }

        @Override
        protected int getScrollbarPositionX() { return right - 5; }

        public int getRowWidth() { return width; }

        public boolean isFocused() { return MapBrowserScreen.this.getFocused() == this; }

        private class MapEntry extends AlwaysSelectedEntryListWidget.Entry<MapEntry> {
            private final String mapName;

            public MapEntry(String mapName) {
                this.mapName = mapName;
            }

            @Override
            public Text getNarration() { return null; }

            @Override
            public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                textRenderer.drawWithShadow(matrices, mapName, x, y, 0xFFFFFFFF, true);
            }

            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    MapList.this.setSelected(this);
                    return true;
                }
                return false;
            }
        }
    }

    public MapList mapList;

    @Override
    protected void init() {
        ButtonWidget mapsText = ButtonWidget.builder(Text.of("Maps on server"), (button -> {}))
                .dimensions(20, height/7 - 28, (int)(width/3), 20).build();

        mapList = new MapList(client, (int)(width/3), (int)(height*2/3), height / 7, (int) (6* height / 6.5f), 18, 20);
        ClientPlayNetworking.send(Packets.GET_MAP_NAMES, PacketByteBufs.empty());

        ButtonWidget selectMap = ButtonWidget.builder(Text.of("Select"), (button) -> {
            MapList.MapEntry focused = mapList.getSelectedOrNull();
            if (focused == null) return;

            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeString(focused.mapName);
            msg.writeBoolean(false);
            msg.writeBoolean(true);
            ClientPlayNetworking.send(Packets.MAP_SELECT_DELETE, msg);
            MapBrowserScreen.this.close();
        }).dimensions(20, (int)(5.75f*height/7) + 4, (int)(width/6.5), 20).build();

        ButtonWidget deleteMap = ButtonWidget.builder(Text.of("Delete"), (button) -> {
            MapList.MapEntry focused = mapList.getSelectedOrNull();
            if (focused == null) return;

            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeString(focused.mapName);
            msg.writeBoolean(true);
            mapList.clearList();
            ClientPlayNetworking.send(Packets.MAP_SELECT_DELETE, msg);
            ClientPlayNetworking.send(Packets.GET_MAP_NAMES, PacketByteBufs.empty());
        }).dimensions(34+(int)(width/6.5), (int)(5.75f*height/7) + 4, (int)(width/6.5), 20).build();

        TextWidget addMapText = new TextWidget(Text.of("Add new map:"), textRenderer);
        addMapText.setPosition(width*2/3, height/3);
        addMapText.setWidth(0);

        TextFieldWidget newMapName = new TextFieldWidget(textRenderer, width*2/3-58, height/3+24, 116, 20, Text.empty());

        ButtonWidget addMap = ButtonWidget.builder(Text.of("Add"), (button) -> {
            if (newMapName.getText().isEmpty()) return;

            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeString(newMapName.getText());
            msg.writeBoolean(false);
            msg.writeBoolean(false);
            mapList.clearList();
            ClientPlayNetworking.send(Packets.MAP_SELECT_DELETE, msg);
            ClientPlayNetworking.send(Packets.GET_MAP_NAMES, PacketByteBufs.empty());
        }).dimensions(width*2/3-32, height/3+52, 60, 20).build();

        addDrawable(mapsText);
        addDrawableChild(mapList);
        addDrawableChild(selectMap);
        addDrawableChild(deleteMap);
        addDrawable(addMapText);
        addDrawableChild(newMapName);
        addDrawableChild(addMap);
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredTextWithShadow(matrices, textRenderer, title, width / 2, 10, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
