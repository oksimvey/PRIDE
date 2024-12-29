package com.robson.pride.api.utils;

import com.robson.pride.mechanics.ParticleTracking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;

public class ArmatureUtils {

    public static Armature getEntityArmature(Entity ent) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                return livingEntityPatch.getArmature();
            }
        }
        return null;
    }

    public static AABB getJointCollider(LocalPlayer renderer, Entity ent, Joint joint) {
        if (renderer != null && ent != null) {
            if (ent instanceof LivingEntity living) {
                ItemStack itemStack = living.getMainHandItem();
                if (joint == Armatures.BIPED.toolL) {
                    itemStack = living.getOffhandItem();
                }
                    return new AABB(getJointWithTranslation(renderer, ent, new Vec3f(- 0.5F, 0, 0), joint).x,
                            getJointWithTranslation(renderer, ent, new Vec3f(0, - 0.5F, 0), joint).y,
                            getJointWithTranslation(renderer, ent,  new Vec3f(0,  0, -1.8F), joint).z,
                            getJointWithTranslation(renderer, ent, new Vec3f(0.2F, 0, 0), joint).x,
                            getJointWithTranslation(renderer, ent, new Vec3f(0,  0.8F, 0), joint).y,
                            getJointWithTranslation(renderer, ent, new Vec3f(0,  0, 0.2F), joint).z);
            }
        }
        return null;
    }

    public static Vec3 getJointWithTranslation(LocalPlayer renderer, Entity ent, Vec3f translation, Joint joint){
        if (renderer != null && ent != null) {
            if (renderer.level().isClientSide ) {
                LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (entitypatch != null) {
                    float interpolation = 0.0F;
                    OpenMatrix4f transformMatrix;
                    if (entitypatch != null) {
                        transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);
                        transformMatrix.translate(translation);
                        OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                        return new Vec3((double) transformMatrix.m30 + (entitypatch.getOriginal()).getX(), (double) transformMatrix.m31 + ((entitypatch.getOriginal()).getY() + (ent.getBbHeight()/1.8) - 1), (double) transformMatrix.m32 + (entitypatch.getOriginal()).getZ());
                    }
                }
            }
        }
        return null;
    }

    public static Vec3 getJoinPosition(LocalPlayer renderer, Entity ent, Joint joint){
        if (renderer != null && ent != null) {
            if (renderer.level().isClientSide ) {
                LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (entitypatch != null) {
                    float interpolation = 0.0F;
                    OpenMatrix4f transformMatrix;
                    if (entitypatch != null) {
                            transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);
                            OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                            return new Vec3((double) transformMatrix.m30 + (entitypatch.getOriginal()).getX(), (double) transformMatrix.m31 + ((entitypatch.getOriginal()).getY() + (ent.getBbHeight()/1.8) - 1), (double) transformMatrix.m32 + (entitypatch.getOriginal()).getZ());
                    }
                }
            }
        }
        return null;
    }
    public static Vec3 getRawJoinPosition(LocalPlayer renderer, Entity ent, Joint joint){
        if (renderer != null && ent != null) {
            if (renderer.level().isClientSide ) {
                LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (entitypatch != null) {
                    float interpolation = 0.0F;
                    OpenMatrix4f transformMatrix;
                    if (entitypatch != null) {
                        transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);
                        OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                        return new Vec3(transformMatrix.m30, transformMatrix.m31, transformMatrix.m32);
                    }
                }
            }
        }
        return null;
    }
}
