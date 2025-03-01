package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.concurrent.TimeUnit;

public class GuardBreak {

    public static void checkForGuardBreak(Entity ent, Entity ddmgent) {
        if (ent != null && ddmgent != null) {
            if (StaminaUtils.getStamina(ent) <= 0.25f) {
                onGuardBreak(ent);
            }
            if (StaminaUtils.getStamina(ddmgent) <= 0.25f) {
                onGuardBreak(ddmgent);
            }
        }
    }

    public static void onGuardBreak(Entity ent) {
        TimerUtil.schedule(() -> {
            ent.getPersistentData().putBoolean("isVulnerable", true);
            StaminaUtils.resetStamina(ent);
        }, 200, TimeUnit.MILLISECONDS);
        PlaySoundUtils.playSound(ent, EpicFightSounds.NEUTRALIZE_BOSSES.get(), 2f, 1f);
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            livingEntityPatch.applyStun(StunType.NEUTRALIZE, 3);
        }
        TimerUtil.schedule(() -> ent.getPersistentData().putBoolean("isVulnerable", false), 2, TimeUnit.SECONDS);
    }

    public static void onVulnerableDamage(Entity ent, LivingAttackEvent event) {
        AttributeUtils.addModifier((LivingEntity) ent, "minecraft:generic.armor", "e0183cbd-f6b9-44b6-8a19-dc729cdef481", -1000, AttributeModifier.Operation.ADDITION);
        ent.getPersistentData().putBoolean("isVulnerable", false);
        PlaySoundUtils.playSoundByString(ent, "pride:execution", 1, 1);
        AnimUtils.playAnimByString(ent, "epicfight:biped/combat/hit_short", 1);
        HealthUtils.hurtEntity(ent, event.getAmount(), event.getSource());
        TimerUtil.schedule(() -> AttributeUtils.removeModifier((LivingEntity) ent, "minecraft:generic.armor", "e0183cbd-f6b9-44b6-8a19-dc729cdef481"), 500, TimeUnit.MILLISECONDS);
    }
}