package com.bytemaniak.mcuake.items.playersettings;

import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.Sounds;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class PlayerSettingsScreen extends Screen {
    private final MCuakePlayer user;

    private class PlayerVoiceList extends AlwaysSelectedEntryListWidget<PlayerVoiceList.PlayerVoiceEntry> {
        public PlayerVoiceList(MinecraftClient client, int x, int y, int width, int height, int itemHeight) {
            super(client, width, height, y, 0, itemHeight);
            this.left = x;
            this.right = x + width;
            this.bottom = y + height;
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
        protected int getScrollbarPositionX() { return this.right - 5; }

        public int getRowWidth() { return this.width; }

        protected boolean isFocused() {
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
                    this.onPressed();
                    return true;
                } else {
                    return false;
                }
            }

            private void onPressed() {
                PlayerVoiceList.this.setSelected(this);
                SoundEvent playerSample = SoundEvent.of(playerSounds.TAUNT);
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(playerSample, 1));
            }
        }

    }

    private static final Identifier TEXTURE = new Identifier("mcuake", "textures/gui/settings.png");
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, width/2 - 99, height/2 - 58, 0, 0, 198, 117);
    }

    private PlayerVoiceList voiceList;
    private ButtonWidget toggleGameMode;

    protected PlayerSettingsScreen(Text title, LivingEntity user) {
        super(title);
        this.user = (MCuakePlayer) user;
    }

    protected void init() {
        voiceList = new PlayerVoiceList(client, width / 2 - 40, height / 2 - 100, 80, 200, 18);
        String buttonText = user.isInQuakeMode() ? "Quake mode" : "Minecraft mode";
        toggleGameMode = ButtonWidget.builder(Text.of(buttonText), (button) -> {
            user.toggleQuakeMode();

            boolean inQuakeMode = user.isInQuakeMode();
            String newButtonText;
            if (inQuakeMode) {
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(Sounds.DAMAGE_DEALT, 1));
                newButtonText = "Quake mode";
            } else {
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1));
                newButtonText = "Minecraft mode";
            }

            this.toggleGameMode.setMessage(Text.of(newButtonText));
            ClientPlayNetworking.send(CSMessages.QUAKE_MODE_UPDATE, PacketByteBufs.empty());
        }).dimensions(width - 120, height - 24, 100, 20).build();
        addDrawable(voiceList);
        addDrawable(toggleGameMode);
        addSelectableChild(voiceList);
        addSelectableChild(toggleGameMode);
        super.init();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        Optional<Element> hovered = hoveredElement(mouseX, mouseY);
        hovered.ifPresent(element -> element.mouseDragged(mouseX, mouseY, button, deltaX, deltaY));
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawBackground(matrices, delta, mouseX, mouseY);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
