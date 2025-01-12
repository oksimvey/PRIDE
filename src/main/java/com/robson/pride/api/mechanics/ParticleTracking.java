package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.TagCheckUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.Objects;
import java.util.Random;

public class ParticleTracking {

    private static boolean fireEnabled = false;

    public static boolean shouldRenderParticle(ItemStack item, Entity ent){
        if (item != null && ent instanceof LivingEntity living) {
            if (item.getTag() != null) {
                String element = item.getTag().getString("passive_element");
                if (Objects.equals(element, "Sun") || TagCheckUtils.itemsTagCheck(item, "passives/sun")){
                    Vec3 vec3 = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR);
                    if (vec3 != null) {
                        if (ElementalUtils.isNotInWater(ent, vec3)){
                            fireEnabled = true;
                            return true;
                        }
                        else if (fireEnabled){
                            Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, ent, SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1, 1);
                            fireEnabled = false;
                        }
                    }
                }
                else return
                        Objects.equals(element, "Darkness") ||
                        Objects.equals(element, "Light") ||
                        Objects.equals(element, "Thunder") ||
                        Objects.equals(element, "Moon") ||
                        Objects.equals(element, "Blood") ||
                        Objects.equals(element, "Wind") ||
                        Objects.equals(element, "Nature") ||
                        Objects.equals(element, "Ice") ||
                        Objects.equals(element, "Water") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/darkness") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/light") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/thunder") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/moon") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/blood") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/wind") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/nature") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/ice") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/water");
            }
        }
        return false;
    }

    public static ParticleOptions getParticle(ItemStack item) {
        if (item != null) {
            return ElementalUtils.getParticleByElement(item.getTag().getString("passive_element"));
        }
        return null;
    }

    public static Vec3f getAABBForImbuement(ItemStack item, Entity ent){
        if (item != null){
            if (TagCheckUtils.itemsTagCheck(item, "tracking_aabb/longsword")) {
                return new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.5F ) * 0.8F + 0.08F, -((new Random()).nextFloat() * (1.5F * ent.getBbHeight() / 1.8F)) - 0.15F);
            }
        }
        return new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.3F) * 0.8F, ((new Random()).nextFloat() - 0.5F) * 0.2F);
    }
}
