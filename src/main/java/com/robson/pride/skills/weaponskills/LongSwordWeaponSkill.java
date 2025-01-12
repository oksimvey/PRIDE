package com.robson.pride.skills.weaponskills;

import com.robson.pride.api.mechanics.PerilousAttack;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.AnimUtils;
import net.minecraft.world.entity.LivingEntity;

public class LongSwordWeaponSkill extends WeaponSkillBase {

    public LongSwordWeaponSkill() {
        super("Common", 10, 3);
    }

    @Override
    public void twohandExecute(LivingEntity ent) {
        if (ent != null) {
                AnimUtils.playAnimByString(ent, "epicfight:biped/combat/tachi_dash", 0);
                PerilousAttack.setPerilous(ent, "pierce_two_hand", 1500);
        }
    }
}
