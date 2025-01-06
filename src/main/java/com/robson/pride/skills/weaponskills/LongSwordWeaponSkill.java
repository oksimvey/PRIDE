package com.robson.pride.skills.weaponskills;

import com.robson.pride.api.skillcore.SkillBases;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.mechanics.PerilousAttack;
import com.robson.pride.api.skillcore.SkillCore;
import net.minecraft.world.entity.LivingEntity;

public class LongSwordWeaponSkill {

    public static void onExecution(LivingEntity ent){
        if (ent != null) {
            if (SkillBases.consumptionBase(ent, 3, 20)) {
                AnimUtils.playAnimByString(ent, "epicfight:biped/combat/tachi_dash", 0);
                PerilousAttack.setPerilous(ent, "pierce_two_hand", 1500);
            }
        }
    }
}
