package com.bytemaniak.mcquake3.sound;

import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class LightningActive extends WeaponActive {
    public LightningActive(Entity owner, int id) {
        super(owner, Sounds.LIGHTNING_ACTIVE, id);
    }

    @Override
    public void tick() {
        super.tick();

        PlayerEntity player = (PlayerEntity) owner;
        if (!player.isCreative() && player.getInventory().getSlotWithStack(new ItemStack(Weapons.LIGHTNING_CELL)) == -1)
            stopSound();
    }
}
