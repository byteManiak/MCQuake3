package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.util.MultiCollidable;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @WrapOperation(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntityCollisions(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
    private List<VoxelShape> addEntityColliders(World world, Entity entity, Box box, Operation<List<VoxelShape>> original) {
        List<VoxelShape> list = original.call(world, entity, box);

        if (box.getAverageSideLength() < 1.0E-7) return list;

        Predicate<Entity> predicate = e -> e instanceof MultiCollidable;
        List<Entity> ents = world.getOtherEntities(entity, box.expand(1.0E-7), predicate);
        List<VoxelShape> newList = new ArrayList<>();
        for (Entity ent : ents) {
            List<VoxelShape> colliders = ((MultiCollidable)ent).getColliders();
            if (!colliders.isEmpty())
                newList.addAll(colliders);
        }

        if (!newList.isEmpty()) {
            newList.addAll(list);
            return newList;
        }

        return list;
    }
}