package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public interface Kick {

    byte ID = 5;

    WeaponSkillBase DATA = new WeaponSkillBase(ID, "Kick", SkillCore.WeaponArtTier.COMMON, ElementDataManager.NEUTRAL, 4, 1, "pierce_two_hand") {


        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(Animations.TACHI_DASH, null));
        }
    };
}
