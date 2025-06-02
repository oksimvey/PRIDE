package com.robson.pride.skills.weaponskills;

import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.AnimUtils;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.Arrays;
import java.util.List;

public class LongSwordWeaponSkill extends WeaponSkillBase {

    public LongSwordWeaponSkill() {
        super("Common", "Neutral", 10, 3, "pierce_two_hand");
    }

    public List<SkillAnimation> defineMotions(LivingEntity ent){
        return List.of(new SkillAnimation(Animations.TACHI_DASH, null));
    }
}
