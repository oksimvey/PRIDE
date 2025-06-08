package com.robson.pride.events;

import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class OnAttackStartEvent {

    public static void onAttackStart(LivingEntityPatch<?> entitypatch, AttackAnimation animation) {
        if (entitypatch != null && animation != null) {
            Entity target = entitypatch.getTarget();
            if (target != null && target.getPersistentData().getBoolean("isVulnerable")) {
                AnimUtils.cancelMotion(entitypatch.getOriginal());
                AnimUtils.playAnim(entitypatch.getOriginal(), AnimationsRegister.EXECUTE, 0);
            }
        }
    }
}
