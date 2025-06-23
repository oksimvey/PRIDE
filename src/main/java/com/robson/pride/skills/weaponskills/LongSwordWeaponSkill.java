package com.robson.pride.skills.weaponskills;

import com.robson.pride.api.data.manager.DataManager;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.data.types.WeaponSkillData;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public interface LongSwordWeaponSkill {


    WeaponSkillData DATA = new WeaponSkillData("Longsword Pierce", DataManager.LONGSWORD_PIERCE, "pride:models/item/scroll_wind", SkillCore.WeaponArtTier.COMMON, DataManager.NEUTRAL, 10, 3, "pierce_two_hand") {

        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(Animations.TACHI_DASH, null));
        }
    };
}
