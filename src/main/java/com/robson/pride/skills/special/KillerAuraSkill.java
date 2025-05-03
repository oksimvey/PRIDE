package com.robson.pride.skills.special;

import com.robson.pride.api.mechanics.ElementalPassives;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.registries.EffectRegister;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import reascer.wom.gameasset.WOMAnimations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KillerAuraSkill {

   public static void skillStart(Entity ent){
       if (ent != null){
           AnimUtils.playAnim(ent, WOMAnimations.TORMENT_BERSERK_CONVERT, 0);
           if (ent instanceof LivingEntity living){
               living.addEffect(new MobEffectInstance(EffectRegister.KILLER_AURA.get(), 9999999, 1));
           }
       }
   }

    public static void auraDamage(Entity ent, String element, float radius, int power) {
       if (ent != null){
        AABB minMax = new AABB(ent.getX() - radius, ent.getY() - (radius * 0.5), ent.getZ() - radius, ent.getX() + radius, ent.getY() + (radius * 1.5), ent.getZ() + radius);
            List<Entity> listent = ent.level().getEntities(ent, minMax);
            for (Entity entko : listent) {
                if (ent.tickCount % 25 == 0) {
                    if (Objects.equals(element, "Darkness")) {
                        ElementalPassives.darknessPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Light")) {
                        ElementalPassives.lightPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Thunder")) {
                        ElementalPassives.thunderPassive(entko, ent, power, new ArrayList<>());
                    }
                    if (Objects.equals(element, "Sun")) {
                        ElementalPassives.sunPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Moon")) {
                        ElementalPassives.moonPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Blood")) {
                        ElementalPassives.bloodPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Wind")) {
                        ElementalPassives.windPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Nature")) {
                        ElementalPassives.naturePassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Ice")) {
                        ElementalPassives.icePassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Water")) {
                        ElementalPassives.waterPassive(entko, ent, power);
                    }
                }
            }
        }
    }

}
