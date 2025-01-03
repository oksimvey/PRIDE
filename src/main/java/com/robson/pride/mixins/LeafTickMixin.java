package com.robson.pride.mixins;

import com.robson.pride.mechanics.LeavesPhysics;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public abstract class LeafTickMixin {

    @Inject(at = @At("HEAD"), method = "animateTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V")
    private void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        double spawnChance = 1;
        double modifier = 50;
        modifier = modifier / 10f / 75f;
        spawnChance *= modifier;

        while (spawnChance > 0) {
            if (random.nextDouble() < spawnChance) {
                LeavesPhysics.trySpawnLeafParticle(state, world, pos, random);
            }
            spawnChance -= 1;
        }
    }
}
