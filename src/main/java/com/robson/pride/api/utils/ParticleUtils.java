package com.robson.pride.api.utils;

import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

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

    public static void spawnParticleTracked(Player player) {
        if (player != null) {
            PlayerPatch entitypatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            byte numberOf;
            float partialScale;
            float interpolation;
            OpenMatrix4f transformMatrix;
            int i;
            if (entitypatch != null) {
                numberOf = 3;
                partialScale = 1.0F / (float) (numberOf - 1);
                interpolation = 0.0F;

                for (i = 0; i < numberOf; ++i) {
                    transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), Armatures.BIPED.toolR);
                    transformMatrix.translate(new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.5F) * 0.8F + 0.08F, -((new Random()).nextFloat() * 2F) - 0.2F));
                    OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((Player) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                    spawnParticleOnServer(ParticleRegistry.DRAGON_FIRE_PARTICLE.get(),(ServerLevel) player.level(), (double) transformMatrix.m30 + ((Player) entitypatch.getOriginal()).getX(), (double) transformMatrix.m31 + ((Player) entitypatch.getOriginal()).getY(), (double) transformMatrix.m32 + ((Player) entitypatch.getOriginal()).getZ(), 1, (double) (((new Random()).nextFloat() - 0.5F) * 0.02F), (double) (((new Random()).nextFloat() - 0.5F) * 0.02F), (double) (((new Random()).nextFloat() - 0.5F) * 0.02F), 0.1f);
                    interpolation += partialScale;
                }
            }
        }
    }
}




