package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(DimensionArgumentType.class)
public class DimensionArgumentTypeMixin {
    @WrapOperation(method = "listSuggestions", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/CommandSource;getWorldKeys()Ljava/util/Set;"))
    private Set<RegistryKey<World>> hideQuakeDimensionInCommandSuggestions(CommandSource instance, Operation<Set<RegistryKey<World>>> original) {
        Set<RegistryKey<World>> worldKeys = original.call(instance);
        worldKeys.removeIf(e -> e.equals(Blocks.Q3_DIMENSION));
        return worldKeys;
    }

    @WrapOperation(method = "getDimensionArgument", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getWorld(Lnet/minecraft/registry/RegistryKey;)Lnet/minecraft/server/world/ServerWorld;"))
    private static @Nullable ServerWorld invalidateQuakeDimensionAsCommandArgument(MinecraftServer instance, RegistryKey<World> key, Operation<ServerWorld> original) {
        if (key.equals(Blocks.Q3_DIMENSION)) return null;

        return original.call(instance, key);
    }
}
