package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;

@Mod.EventBusSubscriber
public class ParticleUtils {

    public static void spawnParticleOnServer(ParticleOptions particle, Level world, double x, double y, double z, int count, double deltax, double deltay, double deltaz, double speed) {
        if (!world.isClientSide) {
            ((ServerLevel) world).sendParticles(particle, x, y, z, count, deltax, deltay, deltaz, speed);
        }
    }

    public static void spawnParticleRelativeToEntity(ParticleOptions particle, Entity ent, double xoffset, double yoffset, double zoffset, int count, double deltax, double deltay, double deltaz, double speed) {
        if (ent != null) {
            if (!ent.level().isClientSide) {
                spawnParticleOnServer(particle, ent.level(), ent.getX() + (xoffset), ent.getY() + (yoffset), ent.getZ() + (zoffset), count, deltax, deltay, deltaz, speed);
            }
        }
    }

    public static Particle spawnAuraParticle(ParticleOptions particletype, double x, double y, double z, double deltax, double deltay, double deltaz){
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
            return particleEngine.createParticle(particletype, x, y, z, deltax, deltay, deltaz).scale(2f);

    }

    public static void spawnParticleTracked(LocalPlayer renderer, Entity ent, Joint joint, ParticleOptions particle, Vec3f AABB) {
        if (renderer != null && ent != null && particle != null) {
            if (renderer.level().isClientSide) {
                LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (entitypatch != null) {
                    int amount = 1;
                    if (particle == ParticleTypes.SMOKE) {
                        amount = 5;
                    }
                    if (entitypatch.getEntityState().attacking()) {
                        amount = amount * 5;
                    }
                    if (entitypatch != null) {
                        for (int i = 0; i < amount; i++) {
                            Vec3 vec = ArmatureUtils.getJointWithTranslation(renderer, ent, AABB, joint);
                            if (vec != null) {
                                renderer.level().addParticle(particle, vec.x, vec.y, vec.z, ((new Random()).nextFloat() - 0.5F) * 0.02F, (double) (((new Random()).nextFloat() - 0.5F) * 0.02F), ((new Random()).nextFloat() - 0.5F) * 0.02F);
                            }
                            else break;
                        }
                    }
                }
            }
        }
    }
}





