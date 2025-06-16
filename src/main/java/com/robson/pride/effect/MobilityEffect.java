package com.robson.pride.effect;

import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.registries.KeyRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

public class MobilityEffect extends PrideEffectBase {

    private ConcurrentSkipListMap<BlockPos, BlockState> blockPosBlockConcurrentHashMap = new ConcurrentSkipListMap<>();

    private int effecttick;

    public MobilityEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
        this.effecttick = 0;
    }


    public void onEffectStart(LivingEntity ent) {
        if (ent instanceof Player) {
            if (ElementalUtils.getElement(ent).equals("Thunder")) {
                ThunderMobility(ent);
                return;
            }
            if (ElementalUtils.getElement(ent).equals("Moon")) {
                MoonMobility(ent);
                return;
            }
            this.effecttick++;
            serverTick(ent);
            if (!ControllEngine.isKeyDown(KeyRegister.keyActionMobility)) {
                ent.removeEffect(EffectRegister.MOBILITY.get());
                return;
            }
            TimerUtil.schedule(() -> onEffectStart(ent), 100, TimeUnit.MILLISECONDS);
        }
    }

    public void MoonMobility(LivingEntity entity) {
        CameraUtils.correctCamera();
        TimerUtil.schedule(() -> {
            CameraUtils.correctCamera();
            if (Minecraft.getInstance().options.keyUp.isDown()) {
                AnimUtils.playAnim(entity, WOMAnimations.ENDERSTEP_FORWARD, 0);
                return;
            }
            if (Minecraft.getInstance().options.keyLeft.isDown()) {
                AnimUtils.playAnim(entity, WOMAnimations.ENDERSTEP_LEFT, 0);
                return;
            }
            if (Minecraft.getInstance().options.keyRight.isDown()) {
                AnimUtils.playAnim(entity, WOMAnimations.ENDERSTEP_RIGHT, 0);
                return;
            }
            AnimUtils.playAnim(entity, WOMAnimations.ENDERSTEP_BACKWARD, 0);
        }, 100, TimeUnit.MILLISECONDS);
    }

    public void ThunderMobility(LivingEntity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().x, 0 + Minecraft.getInstance().gameRenderer.getMainCamera().getLookVector().y, entity.getDeltaMovement().z);
        CameraUtils.correctCamera();
        List<Vec3> points = ArmatureUtils.getEntityArmatureVecsForParticle(Minecraft.getInstance().player, entity, 2, 0.1f);
        TimerUtil.schedule(() -> {
            if (!points.isEmpty()) {
                for (Vec3 point : points) {
                    Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.ELECTRICITY_PARTICLE.get(), point.x, point.y, point.z, 0, 0, 0);
                }
            }
            CameraUtils.correctCamera();
            PlaySoundUtils.playSound(entity, SoundRegistry.LIGHTNING_CAST.get(), 1, 1);
            Vec3 start = entity.position().add(0, entity.getBbHeight() / 1.8f, 0);
            Vec3 direction = Minecraft.getInstance().gameRenderer.getMainCamera().getEntity().getLookAngle();
            if (Minecraft.getInstance().options.keyUp.isDown()) {
                AnimUtils.playAnim(entity, AnimationsRegister.STEP_FORWARD, 0);
                loopLightningTrail(start, direction, (byte) 0);
                return;
            }
            if (Minecraft.getInstance().options.keyLeft.isDown()) {
                AnimUtils.playAnim(entity, AnimationsRegister.STEP_LEFT, 0);
                loopLightningTrail(start, MathUtils.rotate2DVector(direction, -90), (byte) 0);
                return;
            }
            if (Minecraft.getInstance().options.keyRight.isDown()) {
                AnimUtils.playAnim(entity, AnimationsRegister.STEP_RIGHT, 0);
                loopLightningTrail(start, MathUtils.rotate2DVector(direction, 90), (byte) 0);
                return;
            }
            AnimUtils.playAnim(entity, AnimationsRegister.STEP_BACKWARD, 0);
            loopLightningTrail(start, MathUtils.rotate2DVector(direction, 180), (byte) 0);
        }, 100, TimeUnit.MILLISECONDS);
    }

    public void loopLightningTrail(Vec3 start, Vec3 direction, byte currentloop) {
        if (start != null && direction != null && currentloop < 15) {
            Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.ELECTRICITY_PARTICLE.get(), start.x + (direction.x * currentloop / 2.5),
                    start.y, start.z + (direction.z * currentloop / 2.5), direction.x / (1 + currentloop / 1.5f), 0, direction.z / (1 + currentloop / 1.5f));
            TimerUtil.schedule(() -> loopLightningTrail(start, direction, (byte) (currentloop + 1)), 50, TimeUnit.MILLISECONDS);
        }
    }

    public void serverTick(LivingEntity ent) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null && livingEntityPatch.currentLivingMotion == LivingMotions.RUN) {
                ent.setDeltaMovement(ent.getLookAngle().x, 0, ent.getLookAngle().z);
                Vec3i vec3 = new Vec3i((int) ((int) ent.getLookAngle().scale(1.5).x + ent.getX()), (int) (ent.getY() - 1), (int) ((int) ent.getLookAngle().scale(1.5).z + ent.getZ()));
                storeBlockPos(ent, vec3);
                Vec3 legr = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, ent, Armatures.BIPED.legR);
                Vec3 legl = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, ent, Armatures.BIPED.legL);
                float max = 0.25f;
                float min = -0.25f;
                Random random = new Random();
                if (effecttick % 8 == 0) {
                    Minecraft.getInstance().level.playSound(ent, ent.blockPosition(), SoundRegistry.FROST_STEP.get(), SoundSource.NEUTRAL, 1, 1);
                }
                if (legr != null && legl != null) {
                    for (int i = 0; i < 5; i++) {
                        Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.SNOWFLAKE_PARTICLE.get(),
                                random.nextFloat(max - min) + min + legr.x,
                                random.nextFloat(max - min) + min + legr.y,
                                random.nextFloat(max - min) + min + legr.z, 0, 0, 0);
                        Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.SNOWFLAKE_PARTICLE.get(),
                                random.nextFloat(max - min) + min + legl.x,
                                random.nextFloat(max - min) + min + legl.y,
                                random.nextFloat(max - min) + min + legl.z, 0, 0, 0);
                    }
                }
            }
        }
    }

    public void storeBlockPos(LivingEntity ent, Vec3i center) {
        if (ent != null) {
            if (this.effecttick % 5 == 0) {
                TimerUtil.schedule(() -> {
                    restoreBlockPos(ent);
                    PlaySoundUtils.playSound(ent, SoundRegistry.RAY_OF_FROST.get(), 1, 1);
                }, 1, TimeUnit.SECONDS);
                return;
            }
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
                if (!(ent.level().getBlockState(new BlockPos(pos)).getBlock() instanceof AirBlock) && !blockPosBlockConcurrentHashMap.containsKey(new BlockPos(pos))) {
                    blockPosBlockConcurrentHashMap.put(new BlockPos(pos), ent.level().getBlockState(new BlockPos(pos)));
                }
            }
            for (BlockPos pos : blockPosBlockConcurrentHashMap.keySet()) {
                ent.level().setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
            }
        }
    }


    public void restoreBlockPos(LivingEntity ent) {
        if (ent != null && !blockPosBlockConcurrentHashMap.isEmpty()) {
            for (BlockPos blockPos : blockPosBlockConcurrentHashMap.keySet()) {
                ent.level().setBlockAndUpdate(blockPos, blockPosBlockConcurrentHashMap.get(blockPos));
                blockPosBlockConcurrentHashMap.remove(blockPos);
            }
        }
    }

    public void onEffectEnd(LivingEntity ent) {
        this.effecttick = 0;
        TimerUtil.schedule(() -> restoreBlockPos(ent), 1500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void prideClientTick(LivingEntity ent) {

    }
}
