package com.robson.pride.api.utils;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class StaminaUtils {
    public static void StaminaConsume(Entity ent, float amount) {
        if (ent instanceof Player) {
            ServerPlayerPatch playerEntityPatch = EpicFightCapabilities.getEntityPatch(ent, ServerPlayerPatch.class);
            if (playerEntityPatch != null) {
                playerEntityPatch.setStamina(playerEntityPatch.getStamina() - amount);
            }
        } else {
            AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
            if (livingEntityPatch != null) {
                (livingEntityPatch).setStamina(livingEntityPatch.getStamina() - amount);
            }
        }
    }

    public static void StaminaReset(Entity ent) {
        if (ent instanceof Player) {
            ServerPlayerPatch playerEntityPatch = EpicFightCapabilities.getEntityPatch(ent, ServerPlayerPatch.class);
            if (playerEntityPatch != null) {
                playerEntityPatch.setStamina(playerEntityPatch.getMaxStamina());
            }
        } else {
            AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
            if (livingEntityPatch != null) {
                (livingEntityPatch).setStamina(livingEntityPatch.getMaxStamina());
            }
        }
    }

    public static void StaminaAdd(Entity ent, float amount) {
        if (ent instanceof Player) {
            ServerPlayerPatch playerEntityPatch = EpicFightCapabilities.getEntityPatch(ent, ServerPlayerPatch.class);
            if (playerEntityPatch != null) {
                playerEntityPatch.setStamina(playerEntityPatch.getStamina() + amount);
            }
        } else {
            AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
            if (livingEntityPatch != null) {
                (livingEntityPatch).setStamina(livingEntityPatch.getStamina() + amount);
            }
        }
    }

    public static boolean StaminaCheckEqualOrLess(Entity ent, float amount) {
        boolean staminacheck = false;
        if (ent instanceof Player) {
            ServerPlayerPatch playerEntityPatch = EpicFightCapabilities.getEntityPatch(ent, ServerPlayerPatch.class);
            if (playerEntityPatch != null) {
                if (playerEntityPatch.getStamina() <= amount) {
                    staminacheck = true;
                }
            }
        } else {

            AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
            if (livingEntityPatch != null) {
                if (livingEntityPatch.getStamina() <= amount) {
                    staminacheck = true;
                }
            }
        }
        return staminacheck;
    }
        public static boolean StaminaCheckEqualOrMore (Entity ent,float amount){
            boolean staminacheck = false;
            if (ent instanceof Player) {
                ServerPlayerPatch playerEntityPatch = EpicFightCapabilities.getEntityPatch(ent, ServerPlayerPatch.class);
                if (playerEntityPatch != null) {
                    if (playerEntityPatch.getStamina() >= amount) {
                        staminacheck = true;
                    }
                }
            } else {

                AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
                if (livingEntityPatch != null) {
                    if (livingEntityPatch.getStamina() >= amount) {
                        staminacheck = true;
                    }
                }
            }
            return staminacheck;
        }
    }

