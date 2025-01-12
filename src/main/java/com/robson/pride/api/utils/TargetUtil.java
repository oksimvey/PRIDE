package com.robson.pride.api.utils;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
           AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
           if (livingEntityPatch != null) {
               livingEntityPatch.setAttakTargetSync(targetl);
           }
           if (ent instanceof Mob mob) {
               mob.setTarget(targetl);
           }
       }
    }

    public static void setTargetToLastHitEntity(Entity ent) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                LivingEntity target = livingEntityPatch.getTarget();
                if (target != null){
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
