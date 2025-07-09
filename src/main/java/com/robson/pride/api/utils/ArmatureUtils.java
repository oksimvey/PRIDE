package com.robson.pride.api.utils;

import com.robson.pride.api.utils.math.BezierCurvef;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.math.PrideVec2f;
import com.robson.pride.api.utils.math.PrideVec3f;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.AnimationVariables;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ArmatureUtils {

    private static final byte INTERPOLATION = 0;

    private static ConcurrentHashMap<LivingEntityPatch<?>, JointAnimParameters> PARAMETERS = new ConcurrentHashMap<>();

    public record JointAnimParameters(Joint joint) {

    }

    public static PrideVec3f getRawJoint(LocalPlayer renderer, Entity ent, Joint joint){
        if (renderer != null && renderer.level().isClientSide && ent != null && joint != null){
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null){
                return PrideVec3f.fromMatrix4f(entityPatch.getArmature().getBindedTransformFor(entityPatch.getAnimator().getPose(INTERPOLATION), joint)).correctRot(entityPatch.getOriginal());
            }
        }
        return null;
    }

    public static PrideVec3f getJointWithTranslation(LocalPlayer renderer, LivingEntity ent, PrideVec3f translation, Joint joint) {
        if (renderer != null && translation != null && renderer.level().isClientSide && ent != null && joint != null){
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null){
                return PrideVec3f.fromTranslatedMatrix(entityPatch.getArmature().getBindedTransformFor(entityPatch.getAnimator().getPose(INTERPOLATION), joint),
                                translation.x(), translation.y(), translation.z()).toGlobalPosMatrix(ent);
              }
        }
        return null;
    }


    public static PrideVec3f getJointWithTranslation(LocalPlayer renderer, LivingEntityPatch<?> ent, PrideVec3f translation, Joint joint) {
        if (renderer != null && translation != null && renderer.level().isClientSide && ent != null && joint != null) {
            AnimationPlayer player;
            if (ent.getClientAnimator().currentCompositeMotion() != null){
                player = ent.getClientAnimator().getPlayerFor(ent.getClientAnimator().getCompositeLivingMotion(ent.currentCompositeMotion));
            }
            else player = ent.getClientAnimator().getPlayerFor(null);
            if (player != null && player.getAnimation().get() instanceof StaticAnimation animation && ent.getAnimator().getVariables().get(Animations.ReusableSources.TOOLS_IN_BACK, animation.getAccessor()).isEmpty()) {
                return PrideVec3f.fromTranslatedMatrix(animation.getPoseByTime(ent, player.getElapsedTime(), INTERPOLATION).get(ent.getArmature().searchPathIndex(joint.getName())).toMatrix(),
                        translation.x(), translation.y(), translation.z()).toGlobalPosMatrix(ent.getOriginal());
            }
        }
        return null;
    }


    public static PrideVec3f getJointPosition(LocalPlayer renderer, LivingEntity ent, Joint joint) {
        PrideVec3f pos = getRawJoint(renderer, ent, joint);
        if (pos != null) {
            return pos.toGlobalPosMatrix(ent);
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
                            list.add(
                                    PrideVec3f.fromMatrix4f(
                                    entityPatch.getArmature().getBindedTransformFor(animation.getPoseByTime(entityPatch, time, INTERPOLATION), phase.colliders[0].getFirst()))
                                            .correctRot(entityPatch.getOriginal()).scale(scale));
                        }
                    }
                }
            }
        }
        return list;
    }

    public static void traceEntityOnEntity(LivingEntity ent, Entity targetToTrace, float vectorrot, float entityrot, boolean shouldrotate, int interval, int maxtries) {
        LoopUtils.loopByTimes(i -> {
            if (ent != null && targetToTrace != null) {
                if (shouldrotate) {
                    AnimUtils.rotateTo(targetToTrace, ent.getYRot() + entityrot);
                }
                PrideVec2f lookangle = new PrideVec2f(1, 1).rotate(vectorrot).normalize().scale(targetToTrace.getBbWidth());
                AnimUtils.changeDelta(ent, new Vec3(0, 0 ,0));
                targetToTrace.teleportTo(ent.getX() + lookangle.x(), ent.getY(), ent.getZ()  + lookangle.y());

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

    public static float[] getXBodyRot(LivingEntity ent) {
        if (ent != null) {
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null) {
                    return new float[]{entityPatch.getOriginal().xRotO};
            }
        }
        return new float[]{0};
    }

    public static Joint getNearestJoint(LocalPlayer renderer, LivingEntity ent, Vec3 pos) {
        ConcurrentHashMap<Joint, Float> jointposmap = new ConcurrentHashMap<>();
        List<Joint> joints = getArmatureJoints(ent);
        for (Joint joint : joints) {
            PrideVec3f jointpos = getJointPosition(renderer, ent, joint);
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

    public static List<PrideVec3f> getEntityArmatureVecsForParticle(LocalPlayer renderer, LivingEntity ent, int points, float offset) {
        List<PrideVec3f> vec3List = new ArrayList<>();
        List<Joint> joints = getArmatureJoints(ent);
        if (!joints.isEmpty()) {
            for (Joint joint : joints) {
                for (int i = 0; i < points; i++) {
                    vec3List.add(getJointWithTranslation(renderer, ent, new PrideVec3f(((new Random()).nextFloat() - offset) * offset, ((new Random()).nextFloat() - offset) * offset, ((new Random()).nextFloat() - offset) * offset), joint));
                }
            }
        }
        return vec3List;
    }

    public static List<Joint> getArmatureJoints(LivingEntity ent) {
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
