package com.robson.pride.skills.weaponskills;

import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public interface LongSwordWeaponSkill {

    byte ID = 6;

    WeaponSkillBase DATA = new WeaponSkillBase(ID,"Longsword Pierce", SkillCore.WeaponArtTier.COMMON, ElementDataManager.NEUTRAL, 10, 3, "pierce_two_hand") {

        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(Animations.TACHI_DASH, null));
        }
    };
}
