package com.robson.pride.api.skillcore;

import com.robson.pride.api.mechanics.ElementalPassives;
import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.api.utils.TimerUtil;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.TimeUnit;

public class SkillBases {


    public static boolean canHit(Entity dmgent, Entity target, String skillname, int skillid){
        if (dmgent != null && target != null){
            return target instanceof LivingEntity && !DamageSources.isFriendlyFireBetween(target, dmgent) && target != dmgent && target.getPersistentData().getInt(skillname) != skillid;
        }
        return false;
    }

    public static boolean consumptionBase(LivingEntity ent, float staminaconsumption, float manaconsumption){
        if (ent instanceof Player){
        if (ManaUtils.getMana(ent) >= manaconsumption && StaminaUtils.getStamina(ent) >= staminaconsumption) {
            ManaUtils.consumeMana(ent, manaconsumption);
            StaminaUtils.consumeStamina(ent, staminaconsumption);
            return true;
        }
        }
        else if (StaminaUtils.getStamina(ent) >= staminaconsumption) {
            StaminaUtils.consumeStamina(ent, staminaconsumption);
            return true;
        }
        return false;
    }

    public static boolean checkParticleHit(Particle particle, Entity dmgent, Entity target, String skill, int id, float skillradius){
        if (particle != null && target != null && dmgent != null) {
            if (SkillBases.canHit(dmgent, target, skill, id)) {
                double distance = MathUtils.getTotalDistance(target.getX() - particle.getPos().x, target.getY() - particle.getPos().y, target.getZ() - particle.getPos().z);
                if (distance < skillradius) {
                    target.getPersistentData().putInt(skill, id);
                    return true;
                }
            }
        }
        return false;
    }
}
