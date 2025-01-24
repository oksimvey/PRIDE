package com.robson.pride.api.mechanics;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.registries.WeaponSkillRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ParticleTracking {

    private static ConcurrentHashMap<Entity, Boolean> togglefire = new ConcurrentHashMap<>();

    public static boolean shouldRenderParticle(ItemStack item, Entity ent) {
        if (item != null && ent != null) {
            if (item.getTag() != null) {
                String element = ElementalUtils.getItemElement(item);
                if (element.equals("Sun")) {
                    Vec3 vec3 = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR);
                    if (vec3 != null) {
                        if (ElementalUtils.isNotInWater(ent, vec3)) {
                            togglefire.put(ent, true);
                            return true;
                        } else if (togglefire.get(ent)) {
                            Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, ent, SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1, 1);
                            togglefire.put(ent, false);
                        }
                    }
                }
                else return WeaponSkillRegister.elements.contains(element);
            }
        }
        return false;
    }

    public static ParticleOptions getParticle(ItemStack item) {
        if (item != null) {
            return ElementalUtils.getParticleByElement(ElementalUtils.getItemElement(item));
        }
        return null;
    }

    public static Vec3f getAABBForImbuement(ItemStack item, Entity ent) {
        if (item != null) {
            CompoundTag aabb1 = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem());
            if (aabb1 != null) {
                if (aabb1.contains("minX") && aabb1.contains("maxX") && aabb1.contains("minY") && aabb1.contains("maxY") && aabb1.contains("minZ") && aabb1.contains("maxZ")) {
                    if (aabb1.contains("minX1") && aabb1.contains("maxX1") && aabb1.contains("minY1") && aabb1.contains("maxY1") && aabb1.contains("minZ1") && aabb1.contains("maxZ1")) {
                        Random random = new Random();
                        if (random.nextInt(2) == 0) {
                            return new Vec3f((float) (((new Random()).nextFloat() + aabb1.getDouble("minX1")) * aabb1.getDouble("maxX1")), (float) (((new Random()).nextFloat() + aabb1.getDouble("minY1")) * aabb1.getDouble("maxY1") + (aabb1.getDouble("maxY1") / 10)), (float) (-((new Random()).nextFloat() * (aabb1.getDouble("maxZ1") * ent.getBbHeight() / 1.8F)) + aabb1.getDouble("minZ1")));
                        }
                    }
                    return new Vec3f((float) (((new Random()).nextFloat() + aabb1.getDouble("minX")) * aabb1.getDouble("maxX")), (float) (((new Random()).nextFloat() + aabb1.getDouble("minY")) * aabb1.getDouble("maxY") + (aabb1.getDouble("maxY") / 10)), (float) (-((new Random()).nextFloat() * (aabb1.getDouble("maxZ") * ent.getBbHeight() / 1.8F)) + aabb1.getDouble("minZ")));
                }
            }
        }
        return null;
    }
}
