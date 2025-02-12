package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.SpellUtils;
import com.robson.pride.api.utils.TimerUtil;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.minecraft.world.entity.LivingEntity;
import reascer.wom.gameasset.WOMAnimations;

import java.util.concurrent.TimeUnit;

public class FlameSlashSkill extends WeaponSkillBase {

    public FlameSlashSkill() {
        super("Epic", "Sun", 25, 5);
    }

    @Override
    public void twohandExecute(LivingEntity ent) {

    }

    @Override
    public void onehandExecute(LivingEntity ent) {
        if (ent != null) {
                AnimUtils.playAnim(ent, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_1, 0);
                TimerUtil.schedule(() -> SpellUtils.castMikiriSpell(ent, SpellRegistry.FLAMING_STRIKE_SPELL.get(), 3), 400, TimeUnit.MILLISECONDS);
        }
    }
}
