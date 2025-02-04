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

    public static float getDynamicXOffset(float pitch){
        float lookingup = 0.2f;
        float lookingdown = -1.12f;
        float lookingstraight = -0.72f;
        return pitch <= 0 ? lookingup + (pitch + 90) / 90 * (-0.92f) : lookingstraight + (pitch / 90) * (lookingdown + 0.72f);
    }

    public static float getDynamicYOffset(float pitch){
        float lookingup = 0.1f;
        float lookingdown = -0.52f;
        float lookingstraight = 0.52f;
        return pitch <= 0 ? lookingup + (pitch + 90) / 90 * (-0.42f) : lookingstraight + (pitch / 90) * (lookingdown + 0.52f);
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
}
