package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.sound.WeaponActive;
import com.bytemaniak.mcquake3.sound.WeaponHum;
import com.bytemaniak.mcquake3.sound.WeaponSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class WeaponSoundsMixin extends LivingEntity implements WeaponSounds {
    private boolean isHoldingGauntlet = false;
    private boolean isHoldingLightning = false;
    private boolean isHoldingRailgun = false;

    private boolean playingHumSound = false;
    private boolean playingAttackSound = false;

    protected WeaponSoundsMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void handleLoopingWeaponSounds(CallbackInfo ci) {
        if (world.isClient) {
            ItemStack handStack = getMainHandStack();
            if (handStack.getItem() instanceof Weapon weapon) {
                if (handStack.isOf(Weapons.GAUNTLET)) {
                    if (!isHoldingGauntlet) {
                        isHoldingGauntlet = true;
                        playHum(weapon.slot);

                        isHoldingLightning = false;
                        isHoldingRailgun = false;
                    }
                } else if (handStack.isOf(Weapons.LIGHTNING_GUN)) {
                    if (!isHoldingLightning) {
                        isHoldingLightning = true;
                        playHum(weapon.slot);

                        isHoldingGauntlet = false;
                        isHoldingRailgun = false;
                    }
                } else if (handStack.isOf(Weapons.RAILGUN)) {
                    if (!isHoldingRailgun) {
                        isHoldingRailgun = true;
                        playHum(weapon.slot);

                        isHoldingGauntlet = false;
                        isHoldingLightning = false;
                    }
                } else {
                    isHoldingGauntlet = false;
                    isHoldingRailgun = false;
                    isHoldingLightning = false;
                    stopSounds();
                }

                if (weapon.hasActiveLoopSound) {
                    if (!isUsingItem() && playingAttackSound) {
                        playHum(weapon.slot);
                    } else if (isUsingItem() && !playingAttackSound) {
                        playAttackSound(weapon.slot);
                    }
                }
            } else if (playingHumSound || playingAttackSound) {
                isHoldingGauntlet = false;
                isHoldingRailgun = false;
                isHoldingLightning = false;
                stopSounds();
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void playHum(int id) {
        stopSounds();

        SoundManager manager = MinecraftClient.getInstance().getSoundManager();
        WeaponHum humSound = null;

        if (id == Weapons.GAUNTLET.slot)
            humSound = new WeaponHum(this, Sounds.GAUNTLET_HUM, id);
        else if (id == Weapons.RAILGUN.slot)
            humSound = new WeaponHum(this, Sounds.RAILGUN_HUM, id);

        if (humSound != null) {
            manager.play(humSound);
            playingHumSound = true;
        }
    }

    @Environment(EnvType.CLIENT)
    public void stopHum() { playingHumSound = false; }

    @Environment(EnvType.CLIENT)
    public void playAttackSound(int id) {
        stopSounds();

        SoundManager manager = MinecraftClient.getInstance().getSoundManager();
        WeaponActive attackSound = null;

        if (id == Weapons.GAUNTLET.slot)
            attackSound = new WeaponActive(this, Sounds.GAUNTLET_ACTIVE, id);
        else if (id == Weapons.LIGHTNING_GUN.slot)
            attackSound = new WeaponActive(this, Sounds.LIGHTNING_ACTIVE, id);

        if (attackSound != null) {
            manager.play(attackSound);
            playingAttackSound = true;
        }
    }

    @Environment(EnvType.CLIENT)
    public void stopAttackSound() { playingAttackSound = false; }

    @Environment(EnvType.CLIENT)
    private void stopSounds() {
        stopHum();
        stopAttackSound();
    }

    @Environment(EnvType.CLIENT)
    public boolean isPlayingHum() { return playingHumSound; }

    @Environment(EnvType.CLIENT)
    public boolean isPlayingAttack() { return playingAttackSound; }
}
