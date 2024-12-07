package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
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
        Entity target = getTarget(ent);
        if (ent instanceof Player player){
            if (Minecraft.getInstance().player != null){
                LocalPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(Minecraft.getInstance().player, LocalPlayerPatch.class);
                if (playerPatch != null){
                    playerPatch.rotateTo(target, 1000, false);
                }
            }
        }
        else {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null && target != null) {
                livingEntityPatch.rotateTo(target, 1000, false);
            }
        }
    }
}
