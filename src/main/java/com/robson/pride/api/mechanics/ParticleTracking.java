package com.robson.pride.api.mechanics;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.registries.WeaponSkillRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
                        }
                        else if (togglefire.getOrDefault(ent, false)) {
                            Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, ent, SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1, 1);
                            togglefire.put(ent, false);
                        }
                    }
                } else return WeaponSkillRegister.elements.contains(element);
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
            CompoundTag tags = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem());
            if (tags != null) {
                if (tags.contains("colliders")) {
                    ListTag colliders = tags.getList("colliders", 10);
                    CompoundTag collider = colliders.getCompound(new Random().nextInt(colliders.size()));
                    if (collider.contains("minX") && collider.contains("maxX") && collider.contains("minY") && collider.contains("maxY") && collider.contains("minZ") && collider.contains("maxZ")) {
                        return new Vec3f((float) (((new Random()).nextFloat() + collider.getDouble("minX")) * collider.getDouble("maxX")), (float) (((new Random()).nextFloat() + collider.getDouble("minY")) * collider.getDouble("maxY") + (collider.getDouble("maxY") / 10)), (float) (-((new Random()).nextFloat() * (collider.getDouble("maxZ") * ent.getBbHeight() / 1.8F)) + collider.getDouble("minZ")));
                    }
                }
            }
        }
        return new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.3F) * 0.8F, ((new Random()).nextFloat() - 0.5F) * 0.2F);
    }
}
