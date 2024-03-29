package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.sound.SoundUtils;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class PlayerSettingsScreen extends Screen {
    private final QuakePlayer user;

    private class PlayerVoiceList extends AlwaysSelectedEntryListWidget<PlayerVoiceList.PlayerVoiceEntry> {
        public PlayerVoiceList(MinecraftClient client, int x, int y, int width, int height, int itemHeight) {
            super(client, width, height, y, 0, itemHeight);
            this.left = x;
            this.right = x + width;
            this.bottom = y + height;
            addEntry(new PlayerVoiceEntry(Sounds.NONE));
            addEntry(new PlayerVoiceEntry(Sounds.ANGELYSS));
            addEntry(new PlayerVoiceEntry(Sounds.ARACHNA));
            addEntry(new PlayerVoiceEntry(Sounds.ASSASSIN));
            addEntry(new PlayerVoiceEntry(Sounds.AYUMI));
            addEntry(new PlayerVoiceEntry(Sounds.BERET));
            addEntry(new PlayerVoiceEntry(Sounds.GARGOYLE));
            addEntry(new PlayerVoiceEntry(Sounds.KYONSHI));
            addEntry(new PlayerVoiceEntry(Sounds.LIZ));
            addEntry(new PlayerVoiceEntry(Sounds.MAJOR));
            addEntry(new PlayerVoiceEntry(Sounds.MERMAN));
            addEntry(new PlayerVoiceEntry(Sounds.NEKO));
            addEntry(new PlayerVoiceEntry(Sounds.PENGUIN));
            addEntry(new PlayerVoiceEntry(Sounds.SARGE));
            addEntry(new PlayerVoiceEntry(Sounds.SERGEI));
            addEntry(new PlayerVoiceEntry(Sounds.SKELEBOT));
            addEntry(new PlayerVoiceEntry(Sounds.SMARINE));
            addEntry(new PlayerVoiceEntry(Sounds.SORCERESS));
            addEntry(new PlayerVoiceEntry(Sounds.TONY));
            setRenderHorizontalShadows(false);
        }

        @Override
        public void appendNarrations(NarrationMessageBuilder builder) {}

        @Override
        protected int getScrollbarPositionX() { return right - 5; }

        public int getRowWidth() { return width; }

        public boolean isFocused() {
            return PlayerSettingsScreen.this.getFocused() == this;
        }

        private class PlayerVoiceEntry extends AlwaysSelectedEntryListWidget.Entry<PlayerVoiceEntry> {
            private final Sounds.PlayerSounds playerSounds;

            public PlayerVoiceEntry(Sounds.PlayerSounds playerSounds) {
                this.playerSounds = playerSounds;
            }

            @Override
            public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                textRenderer.drawWithShadow(matrices, playerSounds.playerClass, x, y, 0xFFFFFFFF, true);
            }

            @Override
            public Text getNarration() { return null; }

            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    onPressed();
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeString(playerSounds.playerClass);
                    ClientPlayNetworking.send(Packets.PLAYER_CLASS_UPDATE, buf);
                    return true;
                } else {
                    return false;
                }
            }

            private void onPressed() {
                PlayerVoiceList.this.setSelected(this);
                if (!playerSounds.playerClass.equals("None"))
                    SoundUtils.playSoundLocally(SoundEvent.of(playerSounds.TAUNT));
            }
        }

    }

    private ButtonWidget toggleGui;

    public PlayerSettingsScreen(Text title, LivingEntity user) {
        super(title);
        this.user = (QuakePlayer) user;
    }

    protected void init() {
        ButtonWidget voiceSelectionText = ButtonWidget.builder(Text.of("Player voice"), (button -> {}))
                .dimensions(20, 20, (int)(width/6.5f), 20).build();

        String playerVoice = ((QuakePlayer)MinecraftClient.getInstance().player).getPlayerVoice();

        PlayerVoiceList voiceList = new PlayerVoiceList(client, 20, 45, (int) (width / 6.5f), height - 65, 18);
        voiceList.setSelected(voiceList.children().stream()
                .filter(e -> e.playerSounds.playerClass.equals(playerVoice))
                .findFirst().orElse(voiceList.getFirst()));

        String guiButtonText = user.quakeGuiEnabled() ? "Disable Quake GUI" : "Enable Quake GUI";
        toggleGui = ButtonWidget.builder(Text.of(guiButtonText), (button) -> {
            user.toggleQuakeGui();

            boolean quakeGuiEnabled = user.quakeGuiEnabled();
            String newButtonText;
            if (quakeGuiEnabled) {
                SoundUtils.playSoundLocally(Sounds.DAMAGE_DEALT);
                newButtonText = "Disable Quake GUI";
            } else {
                SoundUtils.playSoundLocally(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);
                newButtonText = "Enable Quake GUI";
            }

            toggleGui.setMessage(Text.of(newButtonText));
            ClientPlayNetworking.send(Packets.QUAKE_GUI_UPDATE, PacketByteBufs.empty());
        }).dimensions(width - 140, height - 24, 120, 20).build();

        ButtonWidget giveWeapons =
                ButtonWidget.builder(
                        Text.of("Give me a full arsenal"),
                        (button -> ClientPlayNetworking.send(Packets.FULL_ARSENAL_REQUEST, PacketByteBufs.empty())))
                .dimensions(width / 4, height - 24, width / 3, 20).build();

        addDrawable(voiceSelectionText);
        addDrawableChild(voiceList);
        addDrawableChild(toggleGui);
        addDrawableChild(giveWeapons);
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredTextWithShadow(matrices, textRenderer, title, width / 2, 10, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
