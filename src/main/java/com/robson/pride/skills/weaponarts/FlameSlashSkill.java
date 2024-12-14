package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.api.utils.SpellUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.mechanics.PerilousAttack;
import com.robson.pride.skills.SkillCore;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.TimeUnit;

public class FlameSlashSkill {
    public static void onExecution(LivingEntity ent) {
        if (ent != null) {
            if (SkillCore.consumptionCore(ent, 6, 50)) {
                AnimUtils.playAnimByString(ent,  "wom:biped/combat/solar_obscuridad_auto_1", 0);
                TimerUtil.schedule(()->SpellUtils.castSpell(ent, SpellRegistry.FLAMING_STRIKE_SPELL.get(), 3, 0), 400,  TimeUnit.MILLISECONDS);
                PerilousAttack.setPerilous(ent, "total", 1500);
                spawnParticle(ent);
            }
        }
    }

    public static void spawnParticle(LivingEntity ent){
        if (ent != null){

                loopParticle(ent);
        }
    }

    public static void loopParticle(LivingEntity ent){
        TimerUtil.schedule(()-> spawnParticle(ent), 10, TimeUnit.MILLISECONDS);
    }
}
