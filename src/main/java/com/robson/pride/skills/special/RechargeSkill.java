package com.robson.pride.skills.special;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.KeyRegister;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.events.engine.ControllEngine;

import java.util.concurrent.TimeUnit;

public class RechargeSkill {

    public static void playerRecharge(Entity ent, int duration) {
        if (ent != null) {
            recharge(ent,5,  0, 0);
            AnimUtils.playAnim(ent, AnimationsRegister.RECHARGE, 0.1f);
        }
    }

    public static void recharge(Entity ent, float radius, int maxloops, int currentloop) {
        if (ent != null) {
            ParticleOptions particle = ElementalUtils.getParticleByElement(ElementalUtils.getElement(ent));
            if (particle != null) {
                for (int i = 0; i < radius * 1.5; i++) {
                   ParticleUtils.spawnAuraParticle(particle,(-radius + Math.random() * (radius + radius)) + ent.getX(), ((-radius * 0.5) + Math.random() * ((radius * 1.5) - (-radius * 0.5))) + ent.getY(), (-radius + Math.random() * (radius + radius)) + ent.getZ(), 0,  0, 0);
                }
                rechargeAuraLooper(ent, radius, maxloops, currentloop);
                if (ent instanceof  Player pla){
                    ManaUtils.addMana(pla, 1);
                }
                 if (currentloop % 12 == 0){
                    AnimUtils.playAnim(ent, AnimationsRegister.RECHARGE, 0.1f);
                }
            }
        }
    }

    public static void rechargeAuraLooper(Entity ent, float radius, int maxloops, int currentloop) {
        if (ent instanceof Player) {
            if (ControllEngine.isKeyDown(KeyRegister.keyActionRecharge)) {
                TimerUtil.schedule(() -> recharge(ent, radius, maxloops, currentloop + 1), 50, TimeUnit.MILLISECONDS);
            }
        }
        else if (maxloops > currentloop){
            TimerUtil.schedule(() -> recharge(ent, radius, maxloops, currentloop + 1), 50, TimeUnit.MILLISECONDS);
        }
    }
}

