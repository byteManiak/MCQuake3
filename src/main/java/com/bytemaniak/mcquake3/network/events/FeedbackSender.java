package com.bytemaniak.mcquake3.network.events;

import com.bytemaniak.mcquake3.network.s2c.DealtDamageS2CPacket;
import com.bytemaniak.mcquake3.network.s2c.KilledPlayerS2CPacket;
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

            KilledPlayerS2CPacket buf = new KilledPlayerS2CPacket(gauntletKill);
            ServerPlayNetworking.send((ServerPlayerEntity) entity, buf);
        }
    }

    @Override
    public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity &&
                source.getAttacker() instanceof ServerPlayerEntity player) {
            if (entity.getUuid() != player.getUuid() && source.getName().contains("mcquake3")) {
                DealtDamageS2CPacket buf = new DealtDamageS2CPacket(source.isOf(Q3DamageSources.RAILGUN_DAMAGE));
                ServerPlayNetworking.send(player, buf);
            }
        }

        return true;
    }
}
