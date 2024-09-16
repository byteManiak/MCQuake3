package com.bytemaniak.mcquake3.interfaces;

import net.minecraft.util.shape.VoxelShape;

import java.util.List;

/**
 * An Entity must implement this interface in order to have multiple collision boxes
 */
public interface MultiCollidable {
    /**
     *
     * @return list of colliders contained by the entity, in world space coordinates
     */
    List<VoxelShape> getColliders();
}