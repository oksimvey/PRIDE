package com.robson.pride.skills.weaponskills;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.mechanics.PerilousType;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.data.types.WeaponSkillData;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public interface LongSwordWeaponSkill {


    WeaponSkillData DATA = new WeaponSkillData("Longsword Pierce", ServerDataManager.LONGSWORD_PIERCE, SkillCore.WeaponArtTier.COMMON, ServerDataManager.NEUTRAL, 10, 3, PerilousType.TOTAL) {

        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(Animations.TACHI_DASH, null));
        }
    };
}
