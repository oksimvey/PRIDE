package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public interface GuardBreak {

    List<LivingEntity> EXECUTING = new ArrayList<>();

    static boolean isNeutralized(LivingEntity entity){
        if (entity != null) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                DynamicAnimation currentmotion = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation().orElse(null);
                return currentmotion instanceof StaticAnimation motion && motion == livingEntityPatch.getHitAnimation(StunType.NEUTRALIZE).get();
            }
        }
        return false;
    }

    static void onGuardBreak(LivingEntityPatch<?> entityPatch) {
        entityPatch.applyStun(StunType.NEUTRALIZE, 3);
        PlaySoundUtils.playSound(entityPatch.getOriginal(), EpicFightSounds.NEUTRALIZE_BOSSES.get(), 1, 1);
        AttributeUtils.addModifier(entityPatch.getOriginal(), Attributes.ARMOR, "e0183cbd-f6b9-44b6-8a19-dc729cdef481", -1000, AttributeModifier.Operation.ADDITION);
        TimerUtil.schedule(() -> {
            if (entityPatch != null) {
                AttributeUtils.removeModifier(entityPatch.getOriginal(), Attributes.ARMOR, "e0183cbd-f6b9-44b6-8a19-dc729cdef481");
            }
        }, 500, TimeUnit.MILLISECONDS);
    }

    static void onVulnerableDamage(LivingEntity living) {
        PlaySoundUtils.playSoundByString(living, "pride:execution", 1, 1);
        EXECUTING.remove(living);
    }
}