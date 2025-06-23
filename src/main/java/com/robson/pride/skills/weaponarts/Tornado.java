package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.data.manager.DataManager;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.data.types.WeaponSkillData;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface Tornado {

    WeaponSkillData DATA = new WeaponSkillData("Tornado", DataManager.TORNADO, "pride:models/item/scroll_wind", SkillCore.WeaponArtTier.RARE, DataManager.WIND, 25, 5, "pierce_two_hand") {

        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(AnimationsRegister.GREAT_TACHI_AUTO1, null),
                    new SkillAnimation(AnimationsRegister.GREAT_TACHI_AUTO2, null),
                    new SkillAnimation(AnimationsRegister.GREAT_TACHI_AUTO3, null));
        }
    };
}
