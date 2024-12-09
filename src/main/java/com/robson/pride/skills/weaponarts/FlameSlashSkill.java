package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.SpellUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.mechanics.PerilousAttack;
import com.robson.pride.skills.SkillCore;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.minecraft.world.entity.LivingEntity;

import java.util.concurrent.TimeUnit;

public class FlameSlashSkill {
    public static void onExecution(LivingEntity ent) {
        if (ent != null) {
            if (SkillCore.consumptionCore(ent, 6, 50)) {
                AnimUtils.playAnim(ent,  "wom:biped/combat/solar_obscuridad_auto_1", 0);
                TimerUtil.schedule(()->SpellUtils.castSpell(ent, SpellRegistry.FLAMING_STRIKE_SPELL.get(), 3, 0), 350,  TimeUnit.MILLISECONDS);
                PerilousAttack.setPerilous(ent, "total", 1500);
            }
        }
    }
}
