package com.robson.pride.api.utils;

import com.robson.pride.registries.EffectRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ElementalUtils {

    public static boolean isNotInWater(Entity ent, Vec3 vec3) {
        if (ent instanceof LivingEntity living && vec3 != null) {
            BlockPos pos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
            BlockState blockState = ent.level().getBlockState(pos);
            return !blockState.is(Blocks.WATER) && !ent.level().isRaining() && !living.hasEffect(EffectRegister.WET.get());
        }
        return false;
    }
}
