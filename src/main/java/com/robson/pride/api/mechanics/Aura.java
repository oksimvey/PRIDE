package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.KeyRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.phys.AABB;
import yesman.epicfight.client.events.engine.ControllEngine;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Aura {

    private static int tickcount;

    public static void playerRecharge(Entity ent, int duration) {
        if (ent != null) {
            AttributeUtils.addModifierWithDuration(ent, "irons_spellbooks:mana_regen", 10, duration, AttributeModifier.Operation.ADDITION);
            recharge(ent, ElementalUtils.getElement(ent), 5, 5, 1, true);
            rechargeAuraLooper(ent, 10, duration);
        }
    }

    public static void recharge(Entity ent, String element, float radius, int power, int duration, boolean canhurt) {
        if (ent != null) {
            for (int i = 0; i < radius; i++) {
                ParticleUtils.spawnParticleRelativeToEntity(ParticleRegistry.DRAGON_FIRE_PARTICLE.get(), ent, -radius + Math.random() * (radius + radius), (-radius * 0.5) + Math.random() * ((radius * 1.5) - (-radius * 0.5)), -radius + Math.random() * (radius + radius), 1, 0, 0, 0, 0.1);
           }
            if (!ent.getPersistentData().getBoolean("chargeanim")) {
                AnimUtils.playAnim(ent, AnimationsRegister.RECHARGE, 0.1f);
                ent.getPersistentData().putBoolean("chargeanim", true);
                TimerUtil.schedule(() -> ent.getPersistentData().putBoolean("chargeanim", false), 600, TimeUnit.MILLISECONDS);
            }
            if (canhurt) {
                auraDamage(ent, element, radius, power);
            }
        }
    }

    public static void auraDamage(Entity ent, String element, float radius, int power) {
        AABB minMax = new AABB(ent.getX() - radius, ent.getY() - (radius * 0.5), ent.getZ() - radius, ent.getX() + radius, ent.getY() + (radius * 1.5), ent.getZ() + radius);
        if (ent.level() != null) {
            List<Entity> listent = ent.level().getEntities(ent, minMax);
            for (Entity entko : listent) {
                tickcount++;
                if (tickcount > 25) {
                    tickcount = 0;
                    if (Objects.equals(element, "Darkness")) {
                        ElementalPassives.darknessPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Light")) {
                        ElementalPassives.lightPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Thunder")) {
                        ElementalPassives.thunderPassive(entko, ent, power);
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

    public static void rechargeAuraLooper(Entity ent, int speed, int duration) {
        if (ControllEngine.isKeyDown(KeyRegister.keyActionRecharge)) {
            TimerUtil.schedule(() -> playerRecharge(ent, duration), speed, TimeUnit.MILLISECONDS);
        }
    }
}

