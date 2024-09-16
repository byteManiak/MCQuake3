package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.sound.LightningActive;
import com.bytemaniak.mcquake3.sound.WeaponActive;
import com.bytemaniak.mcquake3.sound.WeaponHum;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class WeaponSoundsMixin extends LivingEntity implements QuakePlayer {
    @Unique private boolean isHoldingGauntlet = false;
    @Unique private boolean isHoldingLightning = false;
    @Unique private boolean isHoldingRailgun = false;

    @Unique private boolean playingHumSound = false;
    @Unique private boolean playingAttackSound = false;

    protected WeaponSoundsMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tick()V"))
    private void handleLoopingWeaponSounds(PlayerEntity player, Operation<Void> original) {
        original.call(player);

        if (player.getWorld().isClient) {
            ItemStack handStack = player.getMainHandStack();
            if (handStack.getItem() instanceof Weapon weapon) {
                if (handStack.isOf(Weapons.GAUNTLET)) {
                    if (!isHoldingGauntlet) {
                        isHoldingGauntlet = true;
                        playHum(player, weapon.slot);

                        isHoldingLightning = false;
                        isHoldingRailgun = false;
                    }
                } else if (handStack.isOf(Weapons.LIGHTNING_GUN)) {
                    if (!isHoldingLightning) {
                        isHoldingLightning = true;
                        playHum(player, weapon.slot);

                        isHoldingGauntlet = false;
                        isHoldingRailgun = false;
                    }
                } else if (handStack.isOf(Weapons.RAILGUN)) {
                    if (!isHoldingRailgun) {
                        isHoldingRailgun = true;
                        playHum(player, weapon.slot);

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
                    if (!player.isUsingItem() && playingAttackSound) {
                        playHum(player, weapon.slot);
                    } else if (player.isUsingItem() && !playingAttackSound) {
                        playAttackSound(player, weapon.slot);
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
    @Unique public void playHum(PlayerEntity player, int id) {
        stopSounds();

        SoundManager manager = MinecraftClient.getInstance().getSoundManager();
        WeaponHum humSound = null;

        if (id == Weapons.GAUNTLET.slot)
            humSound = new WeaponHum(player, Sounds.GAUNTLET_HUM, id);
        else if (id == Weapons.RAILGUN.slot)
            humSound = new WeaponHum(player, Sounds.RAILGUN_HUM, id);

        if (humSound != null) {
            manager.play(humSound);
            playingHumSound = true;
        }
    }

    @Environment(EnvType.CLIENT)
    @Unique public void stopHum() { playingHumSound = false; }

    @Environment(EnvType.CLIENT)
    @Unique public void playAttackSound(PlayerEntity player, int id) {
        stopSounds();

        SoundManager manager = MinecraftClient.getInstance().getSoundManager();
        WeaponActive attackSound = null;

        if (id == Weapons.GAUNTLET.slot)
            attackSound = new WeaponActive(player, Sounds.GAUNTLET_ACTIVE, id);
        else if (id == Weapons.LIGHTNING_GUN.slot)
            attackSound = new LightningActive(player, id);

        if (attackSound != null) {
            manager.play(attackSound);
            playingAttackSound = true;
        }
    }

    @Environment(EnvType.CLIENT)
    @Unique public void stopAttackSound() { playingAttackSound = false; }

    @Environment(EnvType.CLIENT)
    @Unique private void stopSounds() {
        stopHum();
        stopAttackSound();
    }

    @Environment(EnvType.CLIENT)
    @Unique public boolean isPlayingHum() { return playingHumSound; }

    @Environment(EnvType.CLIENT)
    @Unique public boolean isPlayingAttack() { return playingAttackSound; }
}
