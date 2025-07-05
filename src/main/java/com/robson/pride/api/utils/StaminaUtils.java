package com.robson.pride.api.utils;

import com.robson.pride.api.entity.PrideMob;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.mechanics.GuardBreak;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class StaminaUtils {

    public static float getStamina(Entity ent) {
        if (ent != null) {
            LivingEntityPatch<?> mobPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (mobPatch != null) {
                if (mobPatch instanceof PlayerPatch<?> playerPatch) {
                    return playerPatch.getStamina();
                }
                else if (mobPatch instanceof PrideMobPatch<?> prideMobPatch){
                    return prideMobPatch.getStamina();
                }
            }
        }
        return 1;
    }

    public static float getMaxStamina(Entity ent) {
        if (ent != null) {
            LivingEntityPatch<?> mobPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (mobPatch != null) {
                if (mobPatch instanceof PlayerPatch<?> playerPatch) {
                    return playerPatch.getMaxStamina();
                }
                else if (mobPatch instanceof PrideMobPatch<?> prideMobPatch){
                    return prideMobPatch.getMaxStamina();
                }
            }
        }
        return 1;
    }

    public static void setStamina(Entity ent, float amount) {
        if (ent != null) {
            LivingEntityPatch<?> mobPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (mobPatch != null) {
                if (mobPatch instanceof PlayerPatch<?> playerPatch) {
                    playerPatch.setStamina(amount);
                }
                else if (mobPatch instanceof PrideMobPatch<?> prideMobPatch){
                    prideMobPatch.setStamina(amount);
                }
            }
        }
    }

    public static void addStamina(Entity ent, float amount) {
        setStamina(ent, (getStamina(ent) + amount));
    }

    public static void consumeStamina(Entity ent, float amount) {
        if (ent != null) {
            LivingEntityPatch<?> mobPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (mobPatch != null) {
                if (mobPatch instanceof PlayerPatch<?> playerPatch) {
                    if (playerPatch.getStamina() - amount <= 0){
                        playerPatch.setStamina(playerPatch.getMaxStamina());
                        GuardBreak.onGuardBreak(playerPatch);
                        return;
                    }
                    playerPatch.consumeForSkill(EpicFightSkills.BASIC_ATTACK, Skill.Resource.STAMINA, amount);
                }
                else if (mobPatch instanceof PrideMobPatch<?> prideMobPatch){
                    if (prideMobPatch.getStamina() - amount <= 0){
                        prideMobPatch.setStamina(prideMobPatch.getMaxStamina());
                        GuardBreak.onGuardBreak(prideMobPatch);
                        return;
                    }
                    prideMobPatch.consumeStamina(amount);
                }
            }
        }
    }

    public static void resetStamina(Entity ent) {
        setStamina(ent, getMaxStamina(ent));
    }
}