package com.robson.pride.api.utils;

import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.math.BezierCurvef;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.math.PrideVec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ArmatureUtils {

    private static final Vec3f DEFAULT_TRANSLATION = new Vec3f(0.0F, 1.0F, 0.0F);

    private static final byte INTERPOLATION = 0;

    public static PrideVec3f getRawJoint(LocalPlayer renderer, Entity ent, Joint joint){
        if (renderer != null && renderer.level().isClientSide && ent != null && joint != null){
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null){
                OpenMatrix4f matrix4f = entityPatch.getArmature().getBindedTransformFor(entityPatch.getAnimator().getPose(INTERPOLATION), joint);
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) MathUtils.degreeToRadians(entityPatch.getOriginal().yBodyRotO + 180.0F)), DEFAULT_TRANSLATION), matrix4f, matrix4f);
                return new PrideVec3f(matrix4f.m30, matrix4f.m31, matrix4f.m32);
            }
        }
        return null;
    }

    public static PrideVec3f getJointWithTranslation(LocalPlayer renderer, Entity ent, Vec3f translation, Joint joint) {
        if (renderer != null && translation != null && renderer.level().isClientSide && ent != null && joint != null){
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null){
                OpenMatrix4f matrix4f = entityPatch.getArmature().getBindedTransformFor(entityPatch.getAnimator().getPose(INTERPOLATION), joint);
                matrix4f.translate(translation);
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) MathUtils.degreeToRadians(entityPatch.getOriginal().yBodyRotO + 180.0F)), DEFAULT_TRANSLATION), matrix4f, matrix4f);
                return new PrideVec3f((float) (matrix4f.m30 + ent.getX()), (float) (matrix4f.m31 + ent.getY() * (ent.getBbHeight() / 1.8f)), (float) (matrix4f.m32 + ent.getZ()));
            }
        }
        return null;
    }


    public static PrideVec3f getJointWithTranslation(LocalPlayer renderer, LivingEntityPatch<?> ent, Vec3f translation, Joint joint) {
        if (renderer != null && translation != null && renderer.level().isClientSide && ent != null && joint != null){
                OpenMatrix4f matrix4f = ent.getArmature().getBindedTransformFor(ent.getAnimator().getPose(INTERPOLATION), joint);
                matrix4f.translate(translation);
                OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) MathUtils.degreeToRadians(ent.getOriginal().yBodyRotO + 180.0F)), DEFAULT_TRANSLATION), matrix4f, matrix4f);
                return new PrideVec3f((float) (matrix4f.m30 + ent.getOriginal().getX()), (float) (matrix4f.m31 + ent.getOriginal().getY() * (ent.getOriginal().getBbHeight() / 1.8f)), (float) (matrix4f.m32 + ent.getOriginal().getZ()));
        }
        return null;
    }


    public static PrideVec3f getJoinPosition(LocalPlayer renderer, Entity ent, Joint joint) {
        PrideVec3f pos = getRawJoint(renderer, ent, joint);
        if (pos != null) {
            return pos.add(PrideVec3f.fromVec3(ent.position()));
        }
        return null;
    }

    public static List<PrideVec3f> getJointInterpolatedMovement(LocalPlayer renderer, Entity ent, float scale) {
        List<PrideVec3f> list = new ArrayList<>();
        if (renderer != null && renderer.level().isClientSide && ent != null){
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null){
                DynamicAnimation animation = Objects.requireNonNull(entityPatch.getAnimator().getPlayerFor(null)).getAnimation().orElse(null);
                if (animation instanceof AttackAnimation attackAnimation) {
                    for (AttackAnimation.Phase phase : attackAnimation.phases) {
                        float[] phasestime = new float[]{phase.preDelay, phase.contact, phase.recovery};
                        for (float time : phasestime) {
                            OpenMatrix4f matrix4f = entityPatch.getArmature().getBindedTransformFor(animation.getPoseByTime(entityPatch, time, INTERPOLATION), phase.colliders[0].getFirst());
                            OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) MathUtils.degreeToRadians(entityPatch.getOriginal().yBodyRotO + 180.0F)), DEFAULT_TRANSLATION), matrix4f, matrix4f);
                            list.add(new PrideVec3f(matrix4f.m30, matrix4f.m31, matrix4f.m32).scale(scale));
                        }
                    }
                }
            }
        }
        return list;
    }

    public static void traceEntityOnEntityJoint(LivingEntity ent, Entity targetToTrace, Joint toTeleportJoint, Joint targetjoint, boolean shouldrotate, boolean changey, int interval, int maxtries) {
        LoopUtils.loopByTimes(i -> {
            if (ent != null && targetToTrace != null && targetjoint != null && toTeleportJoint != null) {
                if (shouldrotate) {
                    AnimUtils.rotateToEntity(targetToTrace, ent);
                }
                PrideVec3f toteleport = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent, ParticleTracking.getAABBHalf(ent.getMainHandItem(), ent), toTeleportJoint);
                PrideVec3f teleportoffset = ArmatureUtils.getRawJoint(Minecraft.getInstance().player, targetToTrace, targetjoint);
                if (toteleport != null && teleportoffset != null) {
                    Vec3 pos = toteleport.toVec3().add(teleportoffset.toVec3());
                    targetToTrace.teleportTo(pos.x, ent.getY(), pos.z);
                }
            }
        }, maxtries, interval);
    }

    public class SlashParticleParameters {
        public final PrideVec3f pos;

        public final int time;

        public SlashParticleParameters(PrideVec3f pos, int time){
            this.pos = pos;
            this.time = time;
        }
    }

    public static List<PrideVec3f> getJointInterpolatedBezier(LocalPlayer renderer, Entity ent, float scale){
        return BezierCurvef.getBezierInterpolatedPoints(getJointInterpolatedMovement(renderer, ent, scale), (int) (scale * 4));
    }

    public static Joint getNearestJoint(LocalPlayer renderer, Entity ent, Vec3 pos) {
        ConcurrentHashMap<Joint, Float> jointposmap = new ConcurrentHashMap<>();
        List<Joint> joints = getArmatureJoints(ent);
        for (Joint joint : joints) {
            PrideVec3f jointpos = getJoinPosition(renderer, ent, joint);
            if (jointpos != null) {
                jointposmap.put(joint, MathUtils.getTotalDistance(jointpos.toVec3(), pos));
            }
        }
        if (!jointposmap.isEmpty()) {
            Joint joint = null;
            double minValue = Double.MAX_VALUE;
            for (Map.Entry<Joint, Float> entry : jointposmap.entrySet()) {
                if (entry.getValue() < minValue) {
                    minValue = entry.getValue();
                    joint = entry.getKey();
                }
            }
            return joint;
        }
        return null;
    }

    public static List<PrideVec3f> getEntityArmatureVecsForParticle(LocalPlayer renderer, Entity ent, int points, float offset) {
        List<PrideVec3f> vec3List = new ArrayList<>();
        List<Joint> joints = getArmatureJoints(ent);
        if (!joints.isEmpty()) {
            for (Joint joint : joints) {
                for (int i = 0; i < points; i++) {
                    vec3List.add(getJointWithTranslation(renderer, ent, new Vec3f(((new Random()).nextFloat() - offset) * offset, ((new Random()).nextFloat() - offset) * offset, ((new Random()).nextFloat() - offset) * offset), joint));
                }
            }
        }
        return vec3List;
    }

    public static List<Joint> getArmatureJoints(Entity ent) {
        List<Joint> joints = new ArrayList<>();
        if (ent != null) {
            LivingEntityPatch entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null) {
                for (int i = 0; i < entityPatch.getArmature().getJointNumber(); i++) {
                    joints.add(entityPatch.getArmature().searchJointById(i));
                }
            }
        }
        return joints;
    }
}
