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
                if (WeaponSkillRegister.elements.contains(element)){
                   return !element.equals("Sun") || shouldRenderSunParticle(ent);
                }
                if (ent instanceof LivingEntity living && living.hasEffect(EffectRegister.IMBUEMENT.get())){
                    if (living.getEffect(EffectRegister.IMBUEMENT.get()).getEffect() instanceof ImbuementEffect imbuementEffect){
                        if (WeaponSkillRegister.elements.contains(imbuementEffect.element)){
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
        return new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.3F) * 0.3F, ((new Random()).nextFloat() - 0.5F) * 0.2F);
    }
}
