package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.util.MiscUtils;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WeaponPickup extends Pickup {
    protected Weapon weapon;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (!world.isClient) {
            PickupEntity weaponPickup = (PickupEntity) world.getBlockEntity(pos);
            if (entity instanceof ServerPlayerEntity player && weaponPickup.use()) {
                if (!player.getInventory().containsAny(t -> t.isOf(weapon))) {
                    if (((QuakePlayer) player).inQuakeArena()) {
                        player.getInventory().insertStack(weapon.slot, new ItemStack(weapon));

                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeByte(weapon.slot);
                        ServerPlayNetworking.send(player, Packets.SCROLL_TO_SLOT, buf);
                    } else player.giveItemStack(new ItemStack(weapon));
                }

                if (!player.isCreative()) {
                    int countLeft = Weapon.MAX_AMMO - MiscUtils.getCountOfItemType(player.getInventory(), weapon.ammoType);
                    int count = Math.min(weapon.ammoBoxCount, countLeft);
                    ItemStack ammo = new ItemStack(weapon.ammoType, count);
                    MiscUtils.insertInNonHotbarInventory(ammo, player.getInventory());
                }

                world.markDirty(pos);
            }
        }
    }
}
