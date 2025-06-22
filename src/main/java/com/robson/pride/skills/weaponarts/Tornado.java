package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface Tornado {

    byte ID = 3;

    WeaponSkillBase DATA = new WeaponSkillBase(ID, "Tornado", SkillCore.WeaponArtTier.RARE, ElementDataManager.WIND, 25, 5, "pierce_two_hand") {

        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(AnimationsRegister.GREAT_TACHI_AUTO1, null),
                    new SkillAnimation(AnimationsRegister.GREAT_TACHI_AUTO2, null),
                    new SkillAnimation(AnimationsRegister.GREAT_TACHI_AUTO3, null));
        }
    };
}
