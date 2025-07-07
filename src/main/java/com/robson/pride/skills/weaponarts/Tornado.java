package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.mechanics.PerilousType;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.data.types.skill.WeaponSkillData;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface Tornado {

    WeaponSkillData DATA = new WeaponSkillData("Tornado", ServerDataManager.TORNADO,  SkillCore.WeaponArtTier.RARE, ServerDataManager.WIND, 25, 5, PerilousType.TOTAL) {

        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(AnimationsRegister.GREAT_TACHI_AUTO1, null),
                    new SkillAnimation(AnimationsRegister.GREAT_TACHI_AUTO2, null),
                    new SkillAnimation(AnimationsRegister.GREAT_TACHI_AUTO3, null));
        }
    };
}
