package com.robson.pride.skills.weaponskills;

import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.mechanics.PerilousAttack;
import com.robson.pride.skills.SkillCore;
import net.minecraft.world.entity.LivingEntity;

public class LongSwordWeaponSkill {

    public static void onExecution(LivingEntity ent){
        if (ent != null) {
            if (SkillCore.consumptionCore(ent, 3, 20)) {
                AnimUtils.playAnim(ent, "epicfight:biped/combat/tachi_dash", 0);
                PerilousAttack.setPerilous(ent, "pierce_two_hand", 1500);
            }
        }
    }
}
