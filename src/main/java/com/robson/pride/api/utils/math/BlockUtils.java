package com.robson.pride.api.utils.math;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public interface BlockUtils {

    static Vec3 randomDistantPos(Vec3 origin, float min, float max){
        if (origin != null){
            Random random = new Random();
            float radius = random.nextFloat() * (max - min) + min;
            float x = random.nextBoolean() ? radius : -radius;
            float z = random.nextBoolean() ? radius : -radius;
            return origin.add(x, 0, z);
        }
        return null;
    }

    static Vec3 getValidVec3Height(Level level, Vec3 origin, float height){
        BlockPos start = BlockPos.containing(origin);
        while (level.getBlockState(start).isSuffocating(level, start) || level.getBlockState(start.above((int) height)).isSuffocating(level, start.above((int) height))){
            start = start.above();
        }
        while (!level.getBlockState(start.below()).isSuffocating(level, start.below())){
            start = start.below();
        }
        return start.getCenter();
    }

    static boolean isValidVec3(Level level, Vec3 vec3, float radius){
        BlockPos start = BlockPos.containing(vec3);
        for (int i = 0; i < radius; i++) {
            for (int j = 0; j < radius; j++) {
                    if (level.getBlockState(start.offset(i, 0, j)).isSuffocating(level, start.offset(i, 0, j))) {
                        return false;
                    }
            }
        }
        return true;
    }
}
