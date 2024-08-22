package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.registry.Packets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class ArenaBrowserScreen extends Screen {
    public ArenaBrowserScreen(Text title) {
        super(title);
    }

    public class ArenaList extends AlwaysSelectedEntryListWidget<ArenaList.ArenaEntry> {
        public ArenaList(MinecraftClient client, int width, int height, int top, int itemHeight, int x) {
            super(client, width, height, top, itemHeight);
            setPosition(x, top);
        }

        @Override
        public void appendClickableNarrations(NarrationMessageBuilder builder) {}

        public void addArenaEntry(String arenaName) {
            ArenaEntry entry = new ArenaEntry(arenaName);
            addEntry(entry);
            setSelected(entry);
        }

        public void clearList() { clearEntries(); }

        @Override
        protected int getScrollbarPositionX() { return getRight() - 5; }

        public int getRowWidth() { return width; }

        public boolean isFocused() { return ArenaBrowserScreen.this.getFocused() == this; }

        private class ArenaEntry extends AlwaysSelectedEntryListWidget.Entry<ArenaEntry> {
            private final String arenaName;

            public ArenaEntry(String arenaName) {
                this.arenaName = arenaName;
            }

            @Override
            public Text getNarration() { return null; }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                context.drawText(textRenderer, arenaName, x, y, 0xFFFFFFFF, true);
            }

            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    ArenaList.this.setSelected(this);
                    return true;
                }
                return false;
            }
        }
    }

    public ArenaList arenaList;

    @Override
    protected void init() {
        ButtonWidget arenasText = ButtonWidget.builder(Text.of("Arenas on server"), (button -> {}))
                .dimensions(20, height/7 - 28, (int)(width/3), 20).build();

        arenaList = new ArenaList(client, (int)(width/3), (int)(height*2/3), height / 7, 18, 20);
        ClientPlayNetworking.send(Packets.GET_ARENA_NAMES, PacketByteBufs.empty());

        ButtonWidget selectArena = ButtonWidget.builder(Text.of("Select"), (button) -> {
            ArenaList.ArenaEntry focused = arenaList.getSelectedOrNull();
            if (focused == null) return;

            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeString(focused.arenaName);
            msg.writeBoolean(false);
            msg.writeBoolean(true);
            ClientPlayNetworking.send(Packets.ARENA_SELECT_DELETE, msg);
            ArenaBrowserScreen.this.close();
        }).dimensions(20, (int)(5.75f*height/7) + 4, (int)(width/6.5), 20).build();

        ButtonWidget deleteArena = ButtonWidget.builder(Text.of("Delete"), (button) -> {
            ArenaList.ArenaEntry focused = arenaList.getSelectedOrNull();
            if (focused == null) return;

            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeString(focused.arenaName);
            msg.writeBoolean(true);
            arenaList.clearList();
            ClientPlayNetworking.send(Packets.ARENA_SELECT_DELETE, msg);
            ClientPlayNetworking.send(Packets.GET_ARENA_NAMES, PacketByteBufs.empty());
        }).dimensions(34+(int)(width/6.5), (int)(5.75f*height/7) + 4, (int)(width/6.5), 20).build();

        TextWidget addArenaText = new TextWidget(Text.of("Add new arena:"), textRenderer);
        addArenaText.setPosition(width*2/3-48, height/3);
        addArenaText.setWidth(96);

        TextFieldWidget newArenaName = new TextFieldWidget(textRenderer, width*2/3-58, height/3+24, 116, 20, Text.empty());

        ButtonWidget addArena = ButtonWidget.builder(Text.of("Add"), (button) -> {
            if (newArenaName.getText().isEmpty()) return;

            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeString(newArenaName.getText());
            msg.writeBoolean(false);
            msg.writeBoolean(false);
            arenaList.clearList();
            ClientPlayNetworking.send(Packets.ARENA_SELECT_DELETE, msg);
            ClientPlayNetworking.send(Packets.GET_ARENA_NAMES, PacketByteBufs.empty());
        }).dimensions(width*2/3-32, height/3+52, 60, 20).build();

        ButtonWidget leaveQuakeDimension = ButtonWidget.builder(Text.of("Leave Quake dimension"), (button) -> {
            PacketByteBuf msg = PacketByteBufs.create();
            msg.writeBoolean(true);
            ClientPlayNetworking.send(Packets.JOIN_LEAVE_MATCH, msg);
            ArenaBrowserScreen.this.close();
        }).dimensions(width*2/3-64, height/3+104, 128, 20).build();

        addDrawable(arenasText);
        addDrawableChild(arenaList);
        addDrawableChild(selectArena);
        addDrawableChild(deleteArena);

        addDrawable(addArenaText);
        addDrawableChild(newArenaName);
        addDrawableChild(addArena);

        addDrawableChild(leaveQuakeDimension);

        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 10, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }
}
