package com.robson.pride.effect;

import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.registries.KeyRegister;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class MobilityEffect extends PrideEffectBase {

    private ConcurrentHashMap<BlockPos, BlockState> blockPosBlockConcurrentHashMap = new ConcurrentHashMap<>();

    public MobilityEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }


    public void onEffectStart(LivingEntity ent){
        if (ent instanceof Player){
            serverTick(ent);
            if (!ControllEngine.isKeyDown(KeyRegister.keyActionMobility)){
                ent.removeEffect(EffectRegister.MOBILITY.get());
                        return;
            }
            TimerUtil.schedule(()-> onEffectStart(ent), 100, TimeUnit.MILLISECONDS);
        }
    }

    public void serverTick(LivingEntity ent){
        if (ent != null){
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null && livingEntityPatch.currentLivingMotion == LivingMotions.RUN) {
                if (ent.tickCount % 20 == 0){
                   restoreBlockPos(ent);
                }
                ent.setDeltaMovement(ent.getLookAngle().x, ent.getDeltaMovement().y, ent.getLookAngle().z);
                Vec3i vec3 = new Vec3i((int) ((int) ent.getLookAngle().scale(1.5).x + ent.getX()), (int) (ent.getY() - 1), (int) ((int) ent.getLookAngle().scale(1.5).z + ent.getZ()));
                storeBlockPos(ent, vec3);
            }
        }
    }

    public void storeBlockPos(LivingEntity ent, Vec3i center){
        if (ent != null){
            List<Vec3i> positions = Arrays.asList(center,
                    center.offset(-1, 0, -1),
                    center.offset(-1, 0, 0),
                    center.offset(-1, 0, 1),
                    center.offset(0, 0, -1),
                    center.offset(0, 0, 1),
            center.offset(1, 0, -1),
                    center.offset(1, 0, 0),
                    center.offset(1, 0, 1));
            for (Vec3i pos : positions) {
                if (!(ent.level().getBlockState(new BlockPos(pos)).getBlock() instanceof AirBlock)) {
                    blockPosBlockConcurrentHashMap.put(new BlockPos(pos), ent.level().getBlockState(new BlockPos(pos)));
                }
            }
            TimerUtil.schedule(()-> {
                for (Vec3i pos : positions) {
                    ent.level().setBlockAndUpdate(new BlockPos(pos), Blocks.ICE.defaultBlockState());
                }
            }, 100, TimeUnit.MILLISECONDS);
        }
    }

    public void restoreBlockPos(LivingEntity ent){
        if (ent != null && !blockPosBlockConcurrentHashMap.isEmpty()) {
            for (BlockPos blockPos : blockPosBlockConcurrentHashMap.keySet()) {
                ent.level().setBlockAndUpdate(blockPos, blockPosBlockConcurrentHashMap.get(blockPos));
            }
        }
    }

    public void onEffectEnd(LivingEntity ent){
        restoreBlockPos(ent);
    }

    @Override
    public void prideClientTick(LivingEntity ent) {

    }
}
