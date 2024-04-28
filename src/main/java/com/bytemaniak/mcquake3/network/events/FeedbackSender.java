package com.bytemaniak.mcquake3.network.events;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class FeedbackSender implements ServerLivingEntityEvents.AllowDamage, ServerEntityCombatEvents.AfterKilledOtherEntity {
    @Override
    public void afterKilledOtherEntity(ServerWorld world, Entity entity, LivingEntity killedEntity) {
        if (entity instanceof PlayerEntity && killedEntity instanceof PlayerEntity) {
            boolean gauntletKill = false;
            if (killedEntity.getRecentDamageSource() != null)
                gauntletKill = killedEntity.getRecentDamageSource().isOf(Q3DamageSources.GAUNTLET_DAMAGE);

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(gauntletKill);
            ServerPlayNetworking.send((ServerPlayerEntity) entity, Packets.KILLED_PLAYER, buf);
        }
    }

    @Override
    public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity &&
                source.getAttacker() instanceof ServerPlayerEntity player) {
            if (entity.getUuid() != player.getUuid() && source.getName().contains("mcquake3")) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBoolean(source.isOf(Q3DamageSources.RAILGUN_DAMAGE));
                ServerPlayNetworking.send(player, Packets.DEALT_DAMAGE, buf);
            }
        }

        return true;
    }
}
