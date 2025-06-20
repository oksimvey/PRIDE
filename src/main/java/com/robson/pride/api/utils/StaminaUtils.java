package com.robson.pride.api.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class StaminaUtils {

    public static float getStamina(Entity ent) {
        if (ent != null) {
            if (ent instanceof Player) {
                PlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(ent, PlayerPatch.class);
                if (playerPatch != null) {
                    return playerPatch.getStamina();
                }
            } else {
                LivingEntityPatch mobPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (mobPatch != null) {

                }
            }
        }
        return 1;
    }

    public static float getMaxStamina(Entity ent) {
        if (ent != null) {
            if (ent instanceof Player) {
                PlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(ent, PlayerPatch.class);
                if (playerPatch != null) {
                    return playerPatch.getMaxStamina();
                }
            } else {
                LivingEntityPatch mobPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (mobPatch != null) {

                }
            }
        }
        return 1;
    }

    public static void setStamina(Entity ent, float amount) {
        if (ent != null) {
            if (ent instanceof Player) {
                PlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(ent, PlayerPatch.class);
                if (playerPatch != null) {
                    playerPatch.setStamina(amount);
                }
            } else {
                LivingEntityPatch mobPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
                if (mobPatch != null) {

                }
            }
        }
    }

    public static void addStamina(Entity ent, float amount) {
        setStamina(ent, (getStamina(ent) + amount));
    }

    public static void consumeStamina(Entity ent, float amount) {
        setStamina(ent, (getStamina(ent) - amount));
    }

    public static void resetStamina(Entity ent) {
        setStamina(ent, getMaxStamina(ent));
    }
}