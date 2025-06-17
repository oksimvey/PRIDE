package com.robson.pride.api.utils;

import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class TargetUtil {

    public static Entity getTarget(Entity ent) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            return livingEntityPatch.getTarget();
        }
        return null;
    }

    public static void setTarget(Entity ent, Entity target) {
        if (ent != null && target instanceof LivingEntity targetl) {

            if (ent instanceof PrideMobBase mob) {
                mob.setTarget(targetl);
            }
        }
    }

    public static void setTargetToLastHitEntity(Entity ent) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                LivingEntity target = livingEntityPatch.getTarget();
                if (target != null) {
                    setTarget(ent, target);
                }
            }
        }
    }

    public static void rotateToTarget(Entity ent) {
        Entity target = getTarget(ent);
        AnimUtils.rotateToEntity(ent, target);
    }
}
