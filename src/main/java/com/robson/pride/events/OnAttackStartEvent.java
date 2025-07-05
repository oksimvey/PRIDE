package com.robson.pride.events;

import com.robson.pride.api.mechanics.GuardBreak;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.registries.AnimationsRegister;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class OnAttackStartEvent {

    public static void onAttackStart(LivingEntityPatch<?> entitypatch) {
        if (entitypatch != null && entitypatch.getTarget() != null) {
            LivingEntityPatch<?> target = EpicFightCapabilities.getEntityPatch(entitypatch.getTarget(), LivingEntityPatch.class);
            if (target != null && GuardBreak.isNeutralized(target.getOriginal())) {
                GuardBreak.EXECUTING.add(entitypatch.getOriginal());
                AnimationManager.AnimationAccessor<? extends StaticAnimation> motion = AnimationsRegister.EXECUTE;
                int duration = (int) ((motion.get().getTotalTime() / motion.get().getPlaySpeed(entitypatch, motion.get())) * 50f);
                ArmatureUtils.traceEntityOnEntityJoint(
                        entitypatch.getOriginal(),
                        target.getOriginal(),
                        Armatures.BIPED.get().toolR,
                        Armatures.BIPED.get().rootJoint,
                        true,
                        false,
                        100,
                        duration
                );
                entitypatch.playAnimationSynchronized(motion, 0);
            }
        }
    }
}
