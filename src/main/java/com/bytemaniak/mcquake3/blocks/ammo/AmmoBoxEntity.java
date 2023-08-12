package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.registry.Packets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AmmoBoxEntity extends BlockEntity implements GeoBlockEntity {
    private static final long AMMO_BOX_REUSE_TIME = 300;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private long ticksSinceLastUse = AMMO_BOX_REUSE_TIME;
    public boolean lastShouldRender;

    public AmmoBoxEntity(BlockEntityType<? extends AmmoBoxEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
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
        return ticksSinceLastUse > AMMO_BOX_REUSE_TIME;
    }

    public boolean use() {
        if (ticksSinceLastUse > AMMO_BOX_REUSE_TIME) {
            ticksSinceLastUse = 0;
            return true;
        }

        return false;
    }

    @Override
    public void markDirty() {
        if (world.isClient) return;

        PacketByteBuf data = PacketByteBufs.create();
        data.writeBlockPos(getPos());
        data.writeBoolean(shouldRender());
        for (ServerPlayerEntity plr : PlayerLookup.tracking((ServerWorld) world, getPos()))
            ServerPlayNetworking.send(plr, Packets.AMMO_BOX_UPDATE, data);
        super.markDirty();
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T t) {
        if (world.isClient) return;

        AmmoBoxEntity ammoBox = (AmmoBoxEntity) t;
        ammoBox.ticksSinceLastUse++;
        if (ammoBox.lastShouldRender != ammoBox.shouldRender()) {
            ammoBox.markDirty();
        }
        ammoBox.lastShouldRender = ammoBox.shouldRender();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putBoolean("render", lastShouldRender);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        lastShouldRender = nbt.getBoolean("render");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
