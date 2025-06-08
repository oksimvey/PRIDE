package com.robson.pride.api.mechanics;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.elements.ElementBase;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.effect.ImbuementEffect;
import com.robson.pride.registries.EffectRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static com.robson.pride.registries.ElementsRegister.elements;

public class ParticleTracking {

    private static ConcurrentHashMap<Entity, Boolean> togglefire = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<ItemStack, ElementBase> itemParticleMap = new ConcurrentHashMap<>();

    public static boolean shouldRenderParticle(ItemStack item) {
        return item != null && itemParticleMap.get(item) != null;
    }

    public static void tickParticleMapping(ItemStack item, Entity ent) {
        boolean result = false;
        if (item != null && ent != null) {
            if (item.getTag() != null) {
                String element = ElementalUtils.getItemElement(item);
                if (elements.containsKey(element)) {
                    result = !element.equals("Sun") || shouldRenderSunParticle(ent);
                } else if (ent instanceof LivingEntity living && living.hasEffect(EffectRegister.IMBUEMENT.get())) {
                    if (living.getEffect(EffectRegister.IMBUEMENT.get()).getEffect() instanceof ImbuementEffect imbuementEffect) {
                        if (elements.containsKey(imbuementEffect.element) && imbuementEffect.active) {
                            result = !imbuementEffect.element.equals("Sun") || shouldRenderSunParticle(ent);
                        }
                    }
                }
            }
        }
        if (!result) {
            itemParticleMap.remove(item);
        } else itemParticleMap.put(item, getItemElementForImbuement(item, (LivingEntity) ent));
    }

    public static boolean shouldRenderSunParticle(Entity ent) {
        Vec3 vec3 = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR);
        if (vec3 != null) {
            if (ElementalUtils.isNotInWater(ent, vec3)) {
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

    public static ElementBase getItemElementForImbuement(ItemStack item) {
        return item != null && itemParticleMap.get(item) != null ? itemParticleMap.get(item) : null;
    }

    public static ElementBase getItemElementForImbuement(ItemStack item, LivingEntity ent) {
        if (item != null && ent != null) {
            String element = ElementalUtils.getItemElement(item);
            if (elements.containsKey(element)) {
                return elements.get(element);
            } else if (ent.hasEffect(EffectRegister.IMBUEMENT.get()) &&
                    ent.getEffect(EffectRegister.IMBUEMENT.get()).getEffect() instanceof ImbuementEffect imbuementEffect &&
                    elements.containsKey(imbuementEffect.element)) {
                return elements.get(imbuementEffect.element);
            }
        }
        return null;
    }

    public static Vec3f getAABBForImbuement(ItemStack item, Entity ent) {
        if (item != null && ent != null) {
            List<AABB> colliders = PrideCapabilityReloadListener.WEAPON_COLLIDER.get(item.getItem());
            if (colliders != null) {
                AABB collider = colliders.get(new Random().nextInt(colliders.size()));
                return new Vec3f((float) (((new Random()).nextFloat() + collider.minX) * collider.maxX), (float) (((new Random()).nextFloat() + collider.minY) * collider.maxY + (collider.maxY / 10)), (float) (-((new Random()).nextFloat() * (collider.maxZ * ent.getBbHeight() / 1.8F)) + collider.minZ));
            }
        }
        return new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.3F) * 0.3F, ((new Random()).nextFloat() - 0.5F) * 0.2F);
    }
}
