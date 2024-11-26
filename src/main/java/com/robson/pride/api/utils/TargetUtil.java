package com.robson.pride.api.utils;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import net.minecraft.world.entity.Entity;
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

    public static void rotateToTarget(Entity ent) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        Entity target = getTarget(ent);
        if (livingEntityPatch != null && target != null) {
            livingEntityPatch.rotateTo(target, 1000, false);
        }
    }
}
