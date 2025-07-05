package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.mechanics.PerilousType;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.data.types.WeaponSkillData;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public interface Kick {


    WeaponSkillData DATA = new WeaponSkillData("Kick", ServerDataManager.KICK,  SkillCore.WeaponArtTier.COMMON, ServerDataManager.NEUTRAL, 4, 1, PerilousType.TOTAL) {


        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(Animations.TACHI_DASH, null));
        }
    };
}
