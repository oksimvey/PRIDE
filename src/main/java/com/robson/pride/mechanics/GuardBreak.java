package com.robson.pride.mechanics;

import com.robson.pride.api.utils.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.concurrent.TimeUnit;

public class GuardBreak {

    public static void checkForGuardBreak(Entity ent, Entity ddmgent) {
        if (ent != null && ddmgent != null) {
                if (StaminaUtils.StaminaCheckEqualOrLess(ent, 0.1f)) {
                    onGuardBreak(ent);
                }
                if (StaminaUtils.StaminaCheckEqualOrLess(ddmgent, 0.1f)) {
                    onGuardBreak(ddmgent);
                }
        }
    }

    public static void onGuardBreak(Entity ent) {
        TimerUtil.schedule(()-> ent.getPersistentData().putBoolean("isVulnerable", true), 200, TimeUnit.MILLISECONDS);
        PlaySoundUtils.playSound(ent, "epicfight:sfx.neutralize_bosses", 2f, 1f);
        StaminaUtils.StaminaReset(ent);

        if (TagCheckUtils.entityTagCheck(ent, "biped")) {
            AnimUtils.playAnim(ent, "epicfight:biped/skill/guard_break1", 0);
        }
        TimerUtil.schedule(() ->  ent.getPersistentData().putBoolean("isVulnerable", false), 2, TimeUnit.SECONDS);
    }

    public static void onVulnerableDamage(Entity ent, LivingAttackEvent event){
        AttributeUtils.addModifier((LivingEntity) ent, "minecraft:generic.armor", "e0183cbd-f6b9-44b6-8a19-dc729cdef481", -1000, AttributeModifier.Operation.ADDITION);
        ent.getPersistentData().putBoolean("isVulnerable", false);
        PlaySoundUtils.playSound(ent, "pride:execution", 1, 1);
        if (TargetUtil.getTarget(ent) == event.getSource().getEntity()){
            ParticleUtils.bloodGuardBreak(ent);
            TimerUtil.schedule(()->  ParticleUtils.bloodGuardBreak(ent), 300, TimeUnit.MILLISECONDS);
        }
        else ParticleUtils.bloodStealth(ent);
        AnimUtils.playAnim(ent, "epicfight:biped/combat/hit_short", 1);
        HealthUtils.hurtEntity((LivingEntity) ent, event.getAmount(), event.getSource());
        TimerUtil.schedule(()-> AttributeUtils.removeModifier((LivingEntity) ent, "minecraft:generic.armor", "e0183cbd-f6b9-44b6-8a19-dc729cdef481"), 500, TimeUnit.MILLISECONDS);
    }
}