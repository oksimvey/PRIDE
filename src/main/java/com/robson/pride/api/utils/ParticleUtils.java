package com.robson.pride.api.utils;

import com.robson.pride.mechanics.ParticleTracking;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Random;
@Mod.EventBusSubscriber
public class ParticleUtils {

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null) {
            Entity ent = Minecraft.getInstance().player;
            if (Minecraft.getInstance().player.getMainHandItem().getTag()!= null) {
                if (ParticleTracking.shouldRenderParticle(Minecraft.getInstance().player.getMainHandItem())) {
                    spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR, ParticleTracking.getParticle(Minecraft.getInstance().player.getMainHandItem().getTag().getString("passive_element")), new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.5F) * 0.8F + 0.08F, -((new Random()).nextFloat() * 2F) - 0.2F));
                }
            }
                AABB minMax = new AABB(ent.getX() - 10, ent.getY() - 10, ent.getZ() - 10, ent.getX() + 10, ent.getY() + 10, ent.getZ() + 10);
                if (ent.level() != null) {
                    List<Entity> listent = ent.level().getEntities(ent, minMax);
                    for (Entity entko : listent) {
                        spawnParticleTracked(Minecraft.getInstance().player, entko, Armatures.BIPED.toolR, ParticleRegistry.WISP_PARTICLE.get(), new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 2.5F) * 0.2F));
                    }
                }
            }
        }


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
                    if (entitypatch != null) {
                        transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);
                        transformMatrix.translate(AABB);
                        OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                        renderer.level().addParticle(particle, (double) transformMatrix.m30 + (entitypatch.getOriginal()).getX(), (double) transformMatrix.m31 + (entitypatch.getOriginal()).getY(), (double) transformMatrix.m32 + (entitypatch.getOriginal()).getZ(), (double) (((new Random()).nextFloat() - 0.5F) * 0.02F), (double) (((new Random()).nextFloat() - 0.5F) * 0.02F), (double) (((new Random()).nextFloat() - 0.5F) * 0.02F));
                    }
                }
            }
        }
    }
}





