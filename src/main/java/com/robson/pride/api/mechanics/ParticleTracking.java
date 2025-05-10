package com.robson.pride.api.mechanics;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.effect.ImbuementEffect;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.registries.WeaponSkillRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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

public class ParticleTracking {

    private static ConcurrentHashMap<Entity, Boolean> togglefire = new ConcurrentHashMap<>();

    public static boolean shouldRenderParticle(ItemStack item, Entity ent) {
        if (item != null && ent != null) {
            if (item.getTag() != null) {
                String element = ElementalUtils.getItemElement(item);
                if (WeaponSkillRegister.elements.contains(element)){
                   return !element.equals("Sun") || shouldRenderSunParticle(ent);
                }
                if (ent instanceof LivingEntity living && living.hasEffect(EffectRegister.IMBUEMENT.get())){
                    if (living.getEffect(EffectRegister.IMBUEMENT.get()).getEffect() instanceof ImbuementEffect imbuementEffect){
                        if (WeaponSkillRegister.elements.contains(imbuementEffect.element) && imbuementEffect.active){
                          return !imbuementEffect.element.equals("Sun") || shouldRenderSunParticle(ent);
                        }
                    }
                }
                else return WeaponSkillRegister.elements.contains(element);
            }
        }
        return false;
    }

    public static boolean shouldRenderSunParticle(Entity ent){
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

    public static ParticleOptions getParticle(ItemStack item, LivingEntity ent) {
        if (item != null && ent != null) {
            String element = ElementalUtils.getItemElement(item);
            if (WeaponSkillRegister.elements.contains(element)) {
                return ElementalUtils.getParticleByElement(element);
            }
            if (ent.hasEffect(EffectRegister.IMBUEMENT.get())) {
                if (ent.getEffect(EffectRegister.IMBUEMENT.get()).getEffect() instanceof ImbuementEffect imbuementEffect) {
                    if (WeaponSkillRegister.elements.contains(imbuementEffect.element)) {
                        return ElementalUtils.getParticleByElement(imbuementEffect.element);
                    }
                }
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
