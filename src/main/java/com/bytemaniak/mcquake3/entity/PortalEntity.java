package com.bytemaniak.mcquake3.entity;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;

public class PortalEntity extends PropEntity implements GeoEntity {
    private final static TrackedData<Byte> FACING = DataTracker.registerData(PortalEntity.class, TrackedDataHandlerRegistry.BYTE);
    private final static TrackedData<Boolean> ACTIVE = DataTracker.registerData(PortalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final static TrackedData<Integer> XCOORD = DataTracker.registerData(PortalEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final static TrackedData<Integer> YCOORD = DataTracker.registerData(PortalEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final static TrackedData<Integer> ZCOORD = DataTracker.registerData(PortalEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final static TrackedData<Byte> TELEPORT_FACING = DataTracker.registerData(PortalEntity.class, TrackedDataHandlerRegistry.BYTE);

    public PortalEntity(EntityType<?> type, World world) {
        super(type, world, Blocks.PORTAL_ITEM);
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(FACING, (byte) Direction.NORTH.getId());
        dataTracker.startTracking(ACTIVE, false);
        dataTracker.startTracking(XCOORD, 0);
        dataTracker.startTracking(YCOORD, 0);
        dataTracker.startTracking(ZCOORD, 0);
        dataTracker.startTracking(TELEPORT_FACING, (byte)0);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        setFacing(Direction.byId(nbt.getByte("facing")));
        setActive(nbt.getBoolean("active"));
        setTeleportCoords(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
        setTeleportFacing(Direction.byId(nbt.getByte("teleport_facing")));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putByte("facing", dataTracker.get(FACING));
        nbt.putBoolean("active", dataTracker.get(ACTIVE));
        nbt.putFloat("x", dataTracker.get(XCOORD));
        nbt.putFloat("y", dataTracker.get(YCOORD));
        nbt.putFloat("z", dataTracker.get(ZCOORD));
        nbt.putByte("teleport_facing", dataTracker.get(TELEPORT_FACING));
    }

    public void setTeleportFacing(Direction dir) {
        dataTracker.set(TELEPORT_FACING, (byte)dir.getId());
    }

    public Vec3d getTeleportFacingVector() {
        return Vec3d.of(Direction.byId(dataTracker.get(TELEPORT_FACING)).getVector());
    }

    public void setTeleportCoords(int x, int y, int z) {
        dataTracker.set(XCOORD, x);
        dataTracker.set(YCOORD, y);
        dataTracker.set(ZCOORD, z);
    }

    public void setActive(boolean active) {
        dataTracker.set(ACTIVE, active);
    }

    public void setFacing(Direction direction) {
        dataTracker.set(FACING, (byte)direction.getId());
        setYaw(direction.getHorizontal() * 90);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!player.getStackInHand(hand).isOf(Weapons.TOOL)) return ActionResult.FAIL;
        else {
            if (player.isSneaking()) {
                Direction facing = Direction.byId(dataTracker.get(FACING));
                facing = facing.rotateYClockwise();
                setFacing(facing);
            } else ((QuakePlayer)player).setPortalToLink(this);
            return ActionResult.SUCCESS;
        }
    }

    public void teleportEntity(Entity entity) {
        if (dataTracker.get(ACTIVE)) {
            double x = dataTracker.get(XCOORD)+.5;
            double y = dataTracker.get(YCOORD)+.5;
            double z = dataTracker.get(ZCOORD)+.5;
            if (!(entity instanceof PlayerEntity)) y += 1;

            entity.teleport(x, y, z);

            float rotation = Direction.byId(dataTracker.get(TELEPORT_FACING)).asRotation();
            entity.setHeadYaw(rotation);
            entity.setBodyYaw(rotation);
            entity.setYaw(rotation);

            for (int i = 0; i < 10; i++) {
                double rx = x+random.nextDouble()*2-1;
                double ry = y+random.nextDouble()*2-1;
                double rz = z+random.nextDouble()*2-1;
                getWorld().addParticle(ParticleTypes.SMOKE, rx, ry, rz, 0.0, 0.0, 0.0);
                getWorld().addParticle(ParticleTypes.FLAME, rx, ry, rz, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!getWorld().isClient && dataTracker.get(ACTIVE)) {
            Box playerBox = player.getBoundingBox().expand(.1f);
            if (playerBox.intersects(getBoundingBox())) {
                teleportEntity(player);
                getWorld().playSound(player, getBlockPos(), Sounds.TELEPORT_IN, SoundCategory.NEUTRAL);
                getWorld().playSoundFromEntity(null, player, Sounds.TELEPORT_OUT, SoundCategory.NEUTRAL, 1, 1);
            }
       }
    }
}
