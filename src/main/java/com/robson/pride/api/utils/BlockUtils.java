package com.robson.pride.api.utils;

import com.robson.pride.api.utils.math.PrideVec3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public interface BlockUtils {

    List<Block> NON_SOLID = List.of(Blocks.AIR, Blocks.WATER, Blocks.LAVA, Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.CAVE_AIR, Blocks.VOID_AIR);

    static Vec3 tryToFindOwner(PrideVec3f entpos, PrideVec3f ownerpos, float min, float max){
        if (entpos != null && ownerpos != null){
            float scale = new Random().nextFloat(min, max);
            PrideVec3f scaledVector = ownerpos.getDirectionToVector(entpos).scale(scale);
            return ownerpos.add(scaledVector).toVec3();
        }
        return null;
    }

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
       int aboveloops = 0;
       int belowloops = 0;
        while (aboveloops < 50){
            aboveloops++;
            BlockPos start = BlockPos.containing(origin.add(0, aboveloops, 0));
            if(!level.getBlockState(start).isSuffocating(level, start) && !level.getBlockState(start.above((int) height)).isSuffocating(level, start.above((int) height))){
                break;
            }
        }
        while (belowloops < 50) {
            belowloops++;
            BlockPos start = BlockPos.containing(origin.add(0, -belowloops, 0));
            if (!NON_SOLID.contains(level.getBlockState(start).getBlock())){
                break;
            }
        }
        return origin.add(0, aboveloops - belowloops, 0);
    }

    static boolean isValidVec3(Level level, Vec3 vec3, float radius){
        BlockPos start = BlockPos.containing(vec3);
        for (int i = (int) -radius; i < radius; i++) {
            for (int j = (int) -radius; j < radius; j++) {
                    if (level.getBlockState(start.offset(i, 0, j)).isSuffocating(level, start.offset(i, 0, j))) {
                        return false;
                    }
            }
        }
        return true;
    }
}
