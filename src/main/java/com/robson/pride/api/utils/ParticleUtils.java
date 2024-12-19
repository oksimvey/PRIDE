package com.robson.pride.api.utils;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;
@Mod.EventBusSubscriber
public class ParticleUtils {

    public static void spawnParticleOnServer(ParticleOptions particle, ServerLevel world, double x, double y, double z, int count, double deltax, double deltay, double deltaz, double speed) {
        world.sendParticles(particle, x, y, z, count, deltax, deltay, deltaz, speed);
    }

    public static void spawnParticleRelativeToEntity(ParticleOptions particle, Entity ent, double xoffset, double yoffset, double zoffset, int count, double deltax, double deltay, double deltaz, double speed) {
        if (ent != null) {
            spawnParticleOnServer(particle, (ServerLevel) ent.level(), ent.getX() + (xoffset), ent.getY() + (yoffset), ent.getZ() + (zoffset), count, deltax, deltay, deltaz, speed);
        }
    }

    public static void spawnParticleTracked(LocalPlayer renderer, Entity ent, Joint joint, ParticleOptions particle, Vec3f AABB) {
        if (renderer != null && ent != null) {
            if (renderer.level().isClientSide ) {
                LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (entitypatch != null) {
                    float interpolation = 0.0F;
                    OpenMatrix4f transformMatrix;
                    int amount = 1;
                    if (particle == ParticleTypes.SMOKE){
                        amount = 5;
                    }
                    if (entitypatch.getEntityState().attacking()){
                        amount = amount  * 5;
                    }
                    if (entitypatch != null) {
                        for (int i = 0; i < amount; i++) {
                            transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);
                            transformMatrix.translate(AABB);
                            OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                            renderer.level().addParticle(particle, (double) transformMatrix.m30 + (entitypatch.getOriginal()).getX(), (double) transformMatrix.m31 + ((entitypatch.getOriginal()).getY() + (ent.getBbHeight()/1.8) - 1), (double) transformMatrix.m32 + (entitypatch.getOriginal()).getZ(), ((new Random()).nextFloat() - 0.5F) * 0.02F, (double) (((new Random()).nextFloat() - 0.5F) * 0.02F), ((new Random()).nextFloat() - 0.5F) * 0.02F);
                        }
                    }
                }
            }
        }
    }
}





