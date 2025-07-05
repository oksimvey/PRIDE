package com.robson.pride.events;

import com.robson.pride.api.mechanics.GuardBreak;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.math.PrideVec2f;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class OnAttackStartEvent {

    public static void onAttackStart(LivingEntityPatch<?> entitypatch) {
        if (entitypatch != null && entitypatch.getTarget() != null) {
            LivingEntityPatch<?> target = EpicFightCapabilities.getEntityPatch(entitypatch.getTarget(), LivingEntityPatch.class);
            if (target != null && GuardBreak.isNeutralized(target.getOriginal())) {
                PrideVec2f vec2f = PrideVec2f.toPlaneVector(target.getOriginal().getLookAngle()).normalize().scale(target.getOriginal().getBbWidth() + 1);
                Vec3 pos = target.getOriginal().position().add(vec2f.x(), 0, vec2f.y());
                entitypatch.getOriginal().teleportTo(pos.x, pos.y, pos.z);
                AnimUtils.playAnim(entitypatch.getOriginal(), AnimationsRegister.EXECUTE, 0);
            }
        }
    }
}
