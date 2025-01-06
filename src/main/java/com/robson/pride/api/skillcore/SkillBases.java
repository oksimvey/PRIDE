package com.robson.pride.api.skillcore;

import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.StaminaUtils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SkillBases {


    public static boolean canHit(Entity dmgent, Entity target, String skillname, int skillid){
        if (dmgent != null && target != null){
            return target instanceof LivingEntity && !DamageSources.isFriendlyFireBetween(target, dmgent) && target != dmgent && target.getPersistentData().getInt(skillname) != skillid;
        }
        return false;
    }

    public static boolean consumptionBase(LivingEntity ent, float staminaconsumption, float manaconsumption){
        if (ManaUtils.getMana(ent) >= manaconsumption && StaminaUtils.getStamina(ent) >= staminaconsumption){
            ManaUtils.consumeMana(ent, manaconsumption);
            StaminaUtils.consumeStamina(ent, staminaconsumption);
            return true;
        }
        return false;
    }
}
