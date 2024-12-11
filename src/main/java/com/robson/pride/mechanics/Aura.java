package com.robson.pride.mechanics;

import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.PlaySoundUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.KeyRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.phys.AABB;
import yesman.epicfight.client.events.engine.ControllEngine;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Aura {

    public static void recharge(Entity ent, int duration){
        if (ent != null){
            AttributeUtils.addModifierWithDuration(ent, "irons_spellbooks:mana_regen", 5, duration, AttributeModifier.Operation.ADDITION);
            rechargeAura(ent, "sla", 3, 1, false);
            rechargeAuraLooper(ent, 10, duration);
        }
    }

    public static void rechargeAura(Entity ent, String element, int power, int duration, boolean canhurt){
        if (ent != null) {
            double minx = ent.getX() - power;
            double maxx = ent.getX() + power;
            double miny = ent.getY() - power * 0.5;
            double maxy = ent.getY() + power * 1.5;
            double minz = ent.getZ() - power;
            double maxz = ent.getZ() + power;
            if (Minecraft.getInstance().level != null) {
                Minecraft.getInstance().level.addParticle(ParticleRegistry.ELECTRICITY_PARTICLE.get(), minx + Math.random() * (maxx - minx), miny + Math.random() * (maxy - miny), minz + Math.random() * (maxz - minz), 0D, 0D, 0D);
                Minecraft.getInstance().level.addParticle(ParticleRegistry.ELECTRICITY_PARTICLE.get(), minx + Math.random() * (maxx - minx), miny + Math.random() * (maxy - miny), minz + Math.random() * (maxz - minz), 0D, 0D, 0D);

            }
        }
    }

    public static void rechargeAuraLooper(Entity ent, int speed, int duration){
        if (ControllEngine.isKeyDown(KeyRegister.keyActionRecharge)) {
            TimerUtil.schedule(() -> recharge(ent, duration), speed, TimeUnit.MILLISECONDS);
            if (!ent.getPersistentData().getBoolean("chargeanim")) {
                AnimUtils.playAnim(ent, AnimationsRegister.RECHARGE, 0.1f);
                ent.getPersistentData().putBoolean("chargeanim", true);
                TimerUtil.schedule(()->ent.getPersistentData().putBoolean("chargeanim", false), 600, TimeUnit.MILLISECONDS);
                PlaySoundUtils.playSound(ent, "irons_spellbooks:loop.electrocute", 1f, 1f);
            }
            AABB minMax = new AABB(ent.getX()-2, ent.getY() - 2, ent.getZ()-2, ent.getX() + 2, ent.getY()+2, ent.getZ() + 2);
            if (ent.level()!= null) {
                List<Entity> listent = ent.level().getEntities(ent, minMax);
                for (Entity entko : listent) {
                    entko.setSecondsOnFire(2);
                }

            }
        }}
    }

