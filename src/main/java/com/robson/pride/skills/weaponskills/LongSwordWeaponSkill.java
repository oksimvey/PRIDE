package com.robson.pride.skills.weaponskills;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.AnimUtils;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

public class LongSwordWeaponSkill extends WeaponSkillBase {

    public LongSwordWeaponSkill() {
        super("Common", "Neutral", 10, 3, 40);
    }

    @Override
    public void twohandExecute(LivingEntity ent) {
        if (ent != null) {
          AnimUtils.playAnimWithPerilous(ent, Animations.TACHI_DASH, "pierce_two_hand", 0);
        }
    }

    @Override
    public void onehandExecute(LivingEntity ent) {

    }
}
