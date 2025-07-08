package com.robson.pride.events;

import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.skills.special.Vulnerability;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class OnAttackStartEvent {

    public static void onAttackStart(LivingEntityPatch<?> entitypatch) {
        if (entitypatch != null && entitypatch.getTarget() != null) {
            LivingEntityPatch<?> target = EpicFightCapabilities.getEntityPatch(entitypatch.getTarget(), LivingEntityPatch.class);
            if (target != null){
                boolean stealth = Vulnerability.canBackStab(entitypatch.getOriginal().position(), target.getOriginal());
               if (stealth){
                    Vulnerability.setVulnerable(target.getOriginal(), 3000, false);
                }
                if (Vulnerability.isVulnerable(target.getOriginal())){
                    entitypatch.playAnimationSynchronized(AnimationsRegister.EXECUTE, 0);
                    float rot = entitypatch.getOriginal().yBodyRot + 45f;
                    float entityrot = stealth ? 0 : 180f;
                    ArmatureUtils.traceEntityOnEntity(entitypatch.getOriginal(), target.getOriginal(), rot,  entityrot, true,100, 30);
                }
            }
        }
    }
}
