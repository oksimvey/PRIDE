package com.robson.pride.api.utils;

import com.robson.pride.api.utils.math.PrideVec3f;
import com.robson.pride.particles.StringParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public interface ParticleUtils {

    static Particle spawnParticle(ParticleOptions particle, Entity entity, float x, float y, float z, float dx, float dy, float dz) {
        if (entity != null) {
            return spawnParticle(particle, (float) (entity.getX() + x), (float) (entity.getY() + y), (float) (entity.getZ() + z), dx, dy, dz);
        }
        return null;
    }


    static Particle spawnParticle(ParticleOptions particle, PrideVec3f vec3f, float dx, float dy, float dz){
        return spawnParticle(particle, vec3f.x(), vec3f.y(), vec3f.z(), dx, dy, dz);
    }

    static Particle spawnParticle(ParticleOptions particle, float x, float y, float z, float dx, float dy, float dz) {
        return Minecraft.getInstance().particleEngine.createParticle(particle, x, y, z, dx, dy, dz);
    }

    static StringParticle spawnStringParticle(Entity ent, String text, StringParticle.StringParticleTypes type, int lifetime) {
        if (ent != null) {
            Vec3 pos = new Vec3(new Random().nextFloat() - ent.getBbWidth() * ent.getBbWidth(), ent.getBbHeight() * 1.25, new Random().nextFloat() - ent.getBbWidth() * ent.getBbWidth());
            StringParticle particle = new StringParticle(Minecraft.getInstance().level, pos.x + ent.getX(), pos.y + ent.getY(), pos.z + ent.getZ(), 0, 0, type.ordinal());
            particle.setparams(lifetime, text);
            particle.setColor((float) ((type.getColor() >> 16) & 0xFF) / 255.0f,
                    (float) ((type.getColor() >> 8) & 0xFF) / 255.0f,
                    (float) (type.getColor() & 0xFF) / 255.0f);
            Minecraft.getInstance().particleEngine.add(particle);
            tpParticleToEnt(ent, particle);
            return particle;
        }
        return null;
    }

    static void tpParticleToEnt(Entity ent, Particle particle) {
        if (ent != null && particle != null) {
            particle.setPos(ent.getX(), ent.getY() + ent.getBbHeight() * 1.25, ent.getZ());
            TimerUtil.schedule(() -> tpParticleToEnt(ent, particle), 50, TimeUnit.MILLISECONDS);
        }
    }

    static void spawnParticleOnServer(ParticleOptions particle, Level world, double x, double y, double z, int count, double deltax, double deltay, double deltaz, double speed) {
        if (!world.isClientSide) {
            ((ServerLevel) world).sendParticles(particle, x, y, z, count, deltax, deltay, deltaz, speed);
        }
    }

    static void spawnParticleRelativeToEntity(ParticleOptions particle, Entity ent, double xoffset, double yoffset, double zoffset, int count, double deltax, double deltay, double deltaz, double speed) {
        if (ent != null) {
            if (!ent.level().isClientSide) {
                spawnParticleOnServer(particle, ent.level(), ent.getX() + (xoffset), ent.getY() + (yoffset), ent.getZ() + (zoffset), count, deltax, deltay, deltaz, speed);
            }
        }
    }

    static Particle spawnAuraParticle(ParticleOptions particletype, double x, double y, double z, double deltax, double deltay, double deltaz) {
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
        return particleEngine.createParticle(particletype, x, y, z, deltax, deltay, deltaz);

    }

    static void spawnParticleTracked(LocalPlayer renderer, LivingEntityPatch<? extends LivingEntity> ent, Joint joint, ParticleOptions particle, Vec3f AABB, int particleDecrease) {
        if (renderer != null && renderer.level().isClientSide && ent != null) {
            int amount = (int) ItemStackUtils.getColliderSize(ent.getOriginal().getMainHandItem()) - new Random().nextInt(particleDecrease);
            if (amount > 0) {
                Vec3 delta = ent.getOriginal().getDeltaMovement();
                for (int i = 0; i < amount; i++) {
                    PrideVec3f vec = ArmatureUtils.getJointWithTranslation(renderer, ent, AABB, joint);
                    if (vec != null) {
                       renderer.level().addParticle(particle, vec.x(), vec.y(), vec.z(),
                                (ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.02F + delta.x,
                                (ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.02F,
                                (ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.02F + delta.z);
                    }
                    else break;
                }
            }
        }
    }
}





