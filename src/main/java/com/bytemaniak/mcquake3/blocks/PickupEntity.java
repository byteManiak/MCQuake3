package com.bytemaniak.mcquake3.blocks;

import com.bytemaniak.mcquake3.util.MiscUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PickupEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final long regenTime;
    private long ticksSinceLastUse;
    public boolean lastShouldRender = true;

    private final SoundEvent useSound;
    private final SoundEvent regenSound;

    public PickupEntity(BlockEntityType<? extends PickupEntity> type, BlockPos pos, BlockState state,
                        SoundEvent useSound, SoundEvent regenSound, float regenTime) {
        super(type, pos, state);
        this.useSound = useSound;
        this.regenSound = regenSound;
        this.regenTime = this.ticksSinceLastUse = MiscUtils.toTicks(regenTime);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE)
                .triggerableAnim("idle", DefaultAnimations.IDLE));
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        triggerAnim("controller", "idle");
    }

    public boolean shouldRender() {
        return ticksSinceLastUse > regenTime;
    }

    public boolean use() {
        if (ticksSinceLastUse > regenTime) {
            ticksSinceLastUse = 0;
            return true;
        }

        return false;
    }

    @Override
    public void markDirty() {
        if (!world.isClient) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeBlockPos(getPos());
            data.writeBoolean(shouldRender());
            ///for (ServerPlayerEntity plr : PlayerLookup.tracking((ServerWorld) world, getPos()))
                ///ServerPlayNetworking.send(plr, Packets.AMMO_BOX_UPDATE, data);
            super.markDirty();
        }
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T t) {
        if (!world.isClient) {
            PickupEntity pickupEntity = (PickupEntity) t;
            pickupEntity.ticksSinceLastUse++;
            if (pickupEntity.lastShouldRender != pickupEntity.shouldRender()) {
                pickupEntity.markDirty();
                if (pickupEntity.shouldRender())
                    world.playSound(null, pos, pickupEntity.regenSound, SoundCategory.NEUTRAL, 1, 1);
                else world.playSound(null, pos, pickupEntity.useSound, SoundCategory.NEUTRAL, 1, 1);
            }
            pickupEntity.lastShouldRender = pickupEntity.shouldRender();
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putBoolean("render", lastShouldRender);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        lastShouldRender = nbt.getBoolean("render");
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
