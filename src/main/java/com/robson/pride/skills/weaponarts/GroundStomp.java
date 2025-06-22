package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public interface GroundStomp {

    byte ID = 4;

    WeaponSkillBase DATA = new WeaponSkillBase(ID, "Ground Stomp", SkillCore.WeaponArtTier.UNCOMMON, (byte) 11, 4, 4, "pierce_two_hand") {


        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(Animations.TACHI_DASH, null));
        }
    };
}
