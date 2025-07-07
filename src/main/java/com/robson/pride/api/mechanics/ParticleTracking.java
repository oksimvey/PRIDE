package com.robson.pride.api.mechanics;

import com.robson.pride.api.data.types.GenericData;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.item.ElementData;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.api.utils.math.PrideVec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


public class ParticleTracking {

    private static ConcurrentHashMap<Entity, Boolean> togglefire = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<ItemStack, ElementData> itemParticleMap = new ConcurrentHashMap<>();

    public static boolean shouldRenderParticle(ItemStack item) {
        return item != null && itemParticleMap.get(item) != null;
    }

    public static void tickParticleMapping(ItemStack item, Entity ent) {
        boolean result = false;
        if (item != null && ent != null) {
            if (item.getTag() != null) {
                byte element = ElementalUtils.getItemElement(item);
                if (ServerDataManager.getElementData(element) != null) {
                    result = element != ServerDataManager.SUN || shouldRenderSunParticle(ent);
                }
            }
        }
        if (!result) {
            itemParticleMap.remove(item);
        } 
        else itemParticleMap.put(item, getItemElementForImbuement(item, (LivingEntity) ent));
    }

    public static boolean shouldRenderSunParticle(Entity ent) {
        PrideVec3f vec3 = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, ent, Armatures.BIPED.get().toolR);
        if (vec3 != null) {
            if (ElementalUtils.isNotInWater(ent, vec3.toVec3())) {
                togglefire.put(ent, true);
                return true;
            }
            if (togglefire.getOrDefault(ent, false)) {
                Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, ent, SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1, 1);
                togglefire.put(ent, false);
            }
        }
        return false;
    }

    public static ElementData getItemElementForImbuement(ItemStack item) {
        return item != null && itemParticleMap.get(item) != null ? itemParticleMap.get(item) : null;
    }

    public static ElementData getItemElementForImbuement(ItemStack item, LivingEntity ent) {
        if (item != null && ent != null) {
            byte element = ElementalUtils.getItemElement(item);
            if (ServerDataManager.getElementData(element) != null) {
                return ServerDataManager.getElementData(element);
            }
        }
        return null;
    }

    public static Vec3f getAABBForImbuement(ItemStack item, Entity ent) {
        if (item != null && ent != null) {
            GenericData data = ServerDataManager.getGenericData(item);
            if (data != null) {
                Matrix2f collider = data.getCollider();
                return new Vec3f((float) ((ThreadLocalRandom.current().nextFloat() + collider.x0()) * collider.x1()), (float) ((ThreadLocalRandom.current().nextFloat() + collider.y0()) * collider.y1() + (collider.y1() / 10)), (float) (-(ThreadLocalRandom.current().nextFloat() * (collider.z1() * ent.getBbHeight() / 1.8F)) + collider.z0()));
            }
        }
        return new Vec3f((ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F, (ThreadLocalRandom.current().nextFloat() - 0.3F) * 0.3F, (ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F);
    }

    public static Vec3f getAABBHalf(ItemStack item, Entity ent) {
        if (item != null && ent != null) {
            GenericData data = ServerDataManager.getGenericData(item);
            if (data != null) {
                Matrix2f collider = data.getCollider();
                return new Vec3f(0, 0, (collider.z1() / 2) + collider.z0());
            }
        }
        return new Vec3f((ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F, (ThreadLocalRandom.current().nextFloat() - 0.3F) * 0.3F, (ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F);
    }

    public static Vec3f getAABBForImbuementDivided(ItemStack item, Entity ent, float dx, float dy, float dz){
        if (item != null && ent != null) {
            GenericData data = ServerDataManager.getGenericData(item);
            if (data != null) {
                Matrix2f collider = data.getCollider();
                return new Vec3f((float) ((ThreadLocalRandom.current().nextFloat() + (collider.x1() / dx)) * collider.x1()), (float) ((ThreadLocalRandom.current().nextFloat() +(collider.y1() / dy ))* collider.y1() + (collider.y1() / 10)), (float) (-(ThreadLocalRandom.current().nextFloat() * (collider.z1() * ent.getBbHeight() / 1.8F)) + collider.z1() / dz));
            }
        }
        return new Vec3f((ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F, (ThreadLocalRandom.current().nextFloat() - 0.3F) * 0.3F, (ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F);
    }
}
