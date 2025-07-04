package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.mechanics.PerilousType;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.data.types.WeaponSkillData;
import com.robson.pride.api.utils.SpellUtils;
import com.robson.pride.api.utils.TimerUtil;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Animations;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface FlameSlashSkill {


    WeaponSkillData DATA = new WeaponSkillData( "Flame Slash", ServerDataManager.FLAME_SLASH,  SkillCore.WeaponArtTier.EPIC, ServerDataManager.SUN, 25, 5, PerilousType.TOTAL) {


        public List<SkillAnimation> defineMotions(LivingEntity ent) {
            return List.of(new SkillAnimation(Animations.TACHI_DASH, () -> TimerUtil.schedule(() ->
                    SpellUtils.castMikiriSpell(ent, SpellRegistry.FLAMING_STRIKE_SPELL.get(), 3), 400, TimeUnit.MILLISECONDS)));
        }
    };
}
