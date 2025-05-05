package com.robson.pride.api.utils;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ArmatureUtils {

    public static Vec3 getJointWithTranslation(LocalPlayer renderer, Entity ent, Vec3f translation, Joint joint) {
        if (renderer != null && ent != null && translation != null) {
            if (renderer.level().isClientSide) {
                LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (entitypatch != null) {
                    float interpolation = 0.0F;
                    OpenMatrix4f transformMatrix;
                    transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);
                    transformMatrix.translate(translation);
                    OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                    return new Vec3((double) transformMatrix.m30 + (entitypatch.getOriginal()).getX(), (double) transformMatrix.m31 + ((entitypatch.getOriginal()).getY() + (ent.getBbHeight() / 1.8) - 1), (double) transformMatrix.m32 + (entitypatch.getOriginal()).getZ());
                }
            }
        }
        return null;
    }

    public static Vec3 getJoinPosition(LocalPlayer renderer, Entity ent, Joint joint) {
        if (renderer != null && ent != null) {
            if (renderer.level().isClientSide) {
                LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (entitypatch != null) {
                    float interpolation = 0.0F;
                    OpenMatrix4f transformMatrix;
                    transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);
                    OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                    return new Vec3((double) transformMatrix.m30 + (entitypatch.getOriginal()).getX(), (double) transformMatrix.m31 + ((entitypatch.getOriginal()).getY() + (ent.getBbHeight() / 1.8) - 1), (double) transformMatrix.m32 + (entitypatch.getOriginal()).getZ());
                }
            }
        }
        return null;
    }

    public static Joint getNearestJoint(LocalPlayer renderer, Entity ent, Vec3 pos) {
        ConcurrentHashMap<Joint, Double> jointposmap = new ConcurrentHashMap<>();
        List<Joint> joints = getArmatureJoints(ent);
        for (Joint joint : joints){
            Vec3 jointpos = getJoinPosition(renderer, ent, joint);
            if (jointpos != null){
                jointposmap.put(joint, MathUtils.getTotalDistance(jointpos, pos));
            }
        }
        if (!jointposmap.isEmpty()){
            Joint joint = null;
            double minValue = Double.MAX_VALUE;
            for (Map.Entry<Joint, Double> entry : jointposmap.entrySet()) {
                if (entry.getValue() < minValue) {
                    minValue = entry.getValue();
                    joint = entry.getKey();
                }
            }
            return joint;
        }
        return null;
    }

    public static List<Vec3> getEntityArmatureVecsForParticle(LocalPlayer renderer, Entity ent, int points, float offset) {
        List<Vec3> vec3List = new ArrayList<>();
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
