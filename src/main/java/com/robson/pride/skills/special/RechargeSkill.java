package com.robson.pride.skills.special;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.KeyRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.client.events.engine.ControllEngine;

import java.util.List;
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
                    List<Vec3> points = ArmatureUtils.getEntityArmatureVecs(Minecraft.getInstance().player, ent, 2, 0.3f);
                    if (!points.isEmpty()){
                        for (Vec3 vec3 : points) {
                            Minecraft.getInstance().particleEngine.createParticle(particle, vec3.x, vec3.y, vec3.z, 0, 0, 0);
                        }
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

