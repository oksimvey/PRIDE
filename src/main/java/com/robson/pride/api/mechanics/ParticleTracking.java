package com.robson.pride.api.mechanics;

import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.manager.WeaponDataManager;
import com.robson.pride.api.data.types.item.GenericItemData;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.data.types.item.WeaponData;
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
import yesman.epicfight.gameasset.Armatures;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


public class ParticleTracking {

    private static List<LivingEntity> togglefire = new ArrayList<>();

    private static ConcurrentHashMap<ItemStack, ElementData> itemParticleMap = new ConcurrentHashMap<>();

    public static boolean shouldRenderParticle(ItemStack item) {
        return item != null && itemParticleMap.get(item) != null;
    }

    public static void tickParticleMapping(ItemStack item, LivingEntity ent) {
        if (item != null && ent != null) {
            if (item.getTag() != null) {
                String element = ElementalUtils.getItemElement(item);
                if ((element.equals("Sun") && shouldRenderSunParticle(ent)) || ElementDataManager.VALID_ELEMENTS.contains(element)) {
                    ElementData data = ElementDataManager.MANAGER.getByKey(element);
                    if (data != null){
                        itemParticleMap.put(item, data);
                        return;
                    }
                }
            }
            itemParticleMap.remove(item);
        }
    }

    public static boolean shouldRenderSunParticle(LivingEntity ent) {
        PrideVec3f vec3 = ArmatureUtils.getJointPosition(Minecraft.getInstance().player, ent, Armatures.BIPED.get().toolR);
        if (vec3 != null) {
            if (ElementalUtils.isNotInWater(ent, vec3.toVec3())) {
                togglefire.add(ent);
                return true;
            }
            if (togglefire.contains(ent)) {
                Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, ent, SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1, 1);
                togglefire.remove(ent);
            }
        }
        return false;
    }

    public static ElementData getItemElementForImbuement(ItemStack item) {
        return item != null && itemParticleMap.get(item) != null ? itemParticleMap.get(item) : null;
    }

    public static PrideVec3f getAABBForImbuement(ItemStack item, Entity ent) {
        if (item != null && ent != null) {
           WeaponData data = WeaponDataManager.MANAGER.getByItem(item);
            if (data != null) {
                Matrix2f collider = data.getCollider();
                return new PrideVec3f((float) ((ThreadLocalRandom.current().nextFloat() + collider.x0()) * collider.x1()), (float) ((ThreadLocalRandom.current().nextFloat() + collider.y0()) * collider.y1() + (collider.y1() / 10)), (float) (-(ThreadLocalRandom.current().nextFloat() * (collider.z1() * ent.getBbHeight() / 1.8F)) + collider.z0()));
            }
        }
        return new PrideVec3f((ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F, (ThreadLocalRandom.current().nextFloat() - 0.3F) * 0.3F, (ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F);
    }

    public static PrideVec3f getAABBForImbuementDivided(ItemStack item, Entity ent, float dx, float dy, float dz){
        if (item != null && ent != null) {
            GenericItemData data = WeaponDataManager.MANAGER.getByItem(item);
            if (data != null) {
                Matrix2f collider = data.getCollider();
                return new PrideVec3f((float) ((ThreadLocalRandom.current().nextFloat() + (collider.x1() / dx)) * collider.x1()), (float) ((ThreadLocalRandom.current().nextFloat() +(collider.y1() / dy ))* collider.y1() + (collider.y1() / 10)), (float) (-(ThreadLocalRandom.current().nextFloat() * (collider.z1() * ent.getBbHeight() / 1.8F)) + collider.z1() / dz));
            }
        }
        return new PrideVec3f((ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F, (ThreadLocalRandom.current().nextFloat() - 0.3F) * 0.3F, (ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.2F);
    }
}
