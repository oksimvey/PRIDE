package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.SpellUtils;
import com.robson.pride.api.utils.TimerUtil;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.minecraft.world.entity.LivingEntity;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.gameasset.Animations;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlameSlashSkill extends WeaponSkillBase {

    public FlameSlashSkill() {
        super("Epic", "Sun", 25, 5, "sweep");
    }

    public List<SkillAnimation> defineMotions(LivingEntity ent){
        return List.of(new SkillAnimation(Animations.TACHI_DASH, ()-> TimerUtil.schedule(() ->
                SpellUtils.castMikiriSpell(ent, SpellRegistry.FLAMING_STRIKE_SPELL.get(), 3), 400, TimeUnit.MILLISECONDS)));
    }
}
