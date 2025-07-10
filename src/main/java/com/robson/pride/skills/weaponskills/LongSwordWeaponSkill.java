package com.robson.pride.skills.weaponskills;

import com.robson.pride.api.mechanics.PerilousType;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.data.types.skill.WeaponSkillData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public interface LongSwordWeaponSkill {


    WeaponSkillData DATA = new WeaponSkillData(new CompoundTag(), "Longsword Pierce", (short) 2, SkillCore.WeaponArtTier.COMMON, (byte) 11, 10, 3, PerilousType.TOTAL) {

        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(Animations.TACHI_DASH, null));
        }
    };
}
