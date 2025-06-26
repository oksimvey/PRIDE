package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.data.types.WeaponSkillData;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public interface Kick {


    WeaponSkillData DATA = new WeaponSkillData("Kick", ServerDataManager.KICK, "pride:models/item/scroll_wind", SkillCore.WeaponArtTier.COMMON, ServerDataManager.NEUTRAL, 4, 1, "pierce_two_hand") {


        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(Animations.TACHI_DASH, null));
        }
    };
}
