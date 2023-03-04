package com.bytemaniak.mcuake.sound;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@Environment(EnvType.CLIENT)
public class TrackedSound extends MovingSoundInstance {
    protected final Entity owner;
    protected final QuakePlayer.WeaponSlot slot;

    public TrackedSound(Entity owner, SoundEvent sound, QuakePlayer.WeaponSlot weaponSlot) {
        super(sound, SoundCategory.NEUTRAL, SoundInstance.createRandom());
        this.owner = owner;
        this.repeat = true;
        this.slot = weaponSlot;
    }

    @Override
    public void tick() {
        this.x = this.owner.getX();
        this.y = this.owner.getY();
        this.z = this.owner.getZ();
    }
}
