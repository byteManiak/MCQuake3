package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.MCuake;
import com.bytemaniak.mcuake.cs.CSMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Rail extends SimpleProjectile {
    private static final int RAIL_DAMAGE = 100;

    public Rail(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, RAIL_DAMAGE, "railgun", 1);
    }

    public Rail(World world) { this(MCuake.RAIL, world); }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (!world.isClient) {
            Vec3d playerPos = this.getOwner().getPos();
            Vec3d endPos = hitResult.getPos();
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeDouble(playerPos.x);
            buf.writeDouble(playerPos.y);
            buf.writeDouble(playerPos.z);
            buf.writeDouble(endPos.x);
            buf.writeDouble(endPos.y);
            buf.writeDouble(endPos.z);
            for (ServerPlayerEntity plr : PlayerLookup.world((ServerWorld) world))
                ServerPlayNetworking.send(plr, CSMessages.SHOW_RAILGUN_TRAIL, buf);
        }

        super.onCollision(hitResult);
    }

    @Override
    public void tick() {
        if (!world.isClient && this.world.getTime() - initTick > lifetimeInTicks) {
            Vec3d playerPos = this.getOwner().getPos();
            Vec3d endPos = this.getPos();
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeDouble(playerPos.x);
            buf.writeDouble(playerPos.y);
            buf.writeDouble(playerPos.z);
            buf.writeDouble(endPos.x);
            buf.writeDouble(endPos.y);
            buf.writeDouble(endPos.z);
            for (ServerPlayerEntity plr : PlayerLookup.world((ServerWorld) world))
                ServerPlayNetworking.send(plr, CSMessages.SHOW_RAILGUN_TRAIL, buf);
        }
        super.tick();
    }

    @Override
    protected ParticleEffect getParticleType() {
        return MCuake.NO_PARTICLE;
    }
}
