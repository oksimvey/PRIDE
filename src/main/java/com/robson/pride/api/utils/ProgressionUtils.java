package com.robson.pride.api.utils;

import com.robson.pride.progression.PlayerProgressionData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class ProgressionUtils {

    public static int getLoadPercentage(LivingEntity ent){
        int load = 0;
        if (ent != null){
            load = Math.round(((AttributeUtils.getAttributeValue(ent, "epicfight:weight") - 40) / (AttributeUtils.getAttributeValue(ent, "pride:max_weight"))) * 100);
        }
        return load;
    }

    public static void addXp(ServerPlayer player, String stat, int amount){
        player.getCapability(PlayerProgressionData.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            if (Objects.equals(stat, "Strength")){
                capability.addStrengthXp(amount);
            }
            if (Objects.equals(stat, "Dexterity")){
                capability.addDexterityXp(amount);
            }
            if (Objects.equals(stat, "Vigor")){
                capability.addVigorXp(amount);
            }
            if (Objects.equals(stat, "Endurance")){
                capability.addEnduranceXp(amount);
            }
            if (Objects.equals(stat, "Mind")){
                capability.addMindXp(amount);
            }
            capability.syncPlayerVariables(player);
        });
    }
}
