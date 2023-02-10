package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.Sounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class Gauntlet extends Weapon {
    private static final long GAUNTLET_REFIRE_RATE = 2;

    public Gauntlet() {
        super(MCuakePlayer.WeaponSlot.GAUNTLET, GAUNTLET_REFIRE_RATE, SoundEvents.BLOCK_DEEPSLATE_TILES_BREAK);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
    }

    @Override
    protected void onWeaponRefireClient(World world, LivingEntity user, ItemStack stack) {
        HitResult hit = MinecraftClient.getInstance().crosshairTarget;
        switch(hit.getType()) {
            case ENTITY:
                EntityHitResult entityHit = (EntityHitResult) hit;
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(entityHit.getEntity().getId());
                ClientPlayNetworking.send(CSMessages.GAUNTLET_DAMAGE, buf);
                break;
            default:
                break;
        }
    }
}
