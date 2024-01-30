package com.bytemaniak.mcquake3.blocks;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.screen.JumppadScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class JumppadEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    public static final float JUMPPAD_ENTITY_POWER_MAX = 9.5f;

    public float forward_power = 0.5f, up_power = 0.5f;

    public long lastTick = 0;

    private UUID lastPlayerUser;
    private JumppadScreenHandler lastScreen;

    public JumppadEntity(BlockPos pos, BlockState state) {
        super(Blocks.JUMPPAD_BLOCK_ENTITY, pos, state);
        lastPlayerUser = UUID.randomUUID();
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        lastScreen = new JumppadScreenHandler(syncId, inv, this);
        return lastScreen;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        forward_power = nbt.getFloat("forward_power");
        up_power = nbt.getFloat("up_power");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putFloat("forward_power", forward_power);
        nbt.putFloat("up_power", up_power);
        super.writeNbt(nbt);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() { return BlockEntityUpdateS2CPacket.create(this); }

    @Override
    public NbtCompound toInitialChunkDataNbt() { return createNbt(); }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeFloat(forward_power);
        buf.writeFloat(up_power);
    }

    public void updatePower(float forward_power, float up_power) {
        this.forward_power = forward_power;
        this.up_power = up_power;
    }

    public PlayerEntity getLastPlayerUser() { return world.getPlayerByUuid(lastPlayerUser); }

    public void setLastPlayerUser(PlayerEntity playerUser) { lastPlayerUser = playerUser.getUuid(); }

    public JumppadScreenHandler getLastScreen() { return lastScreen; }
}