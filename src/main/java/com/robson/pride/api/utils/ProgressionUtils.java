package com.robson.pride.api.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ProgressionUtils {

    public static int getLoadPercentage(LivingEntity ent) {
        int load = 0;
        if (ent != null) {
            load = Math.round(((AttributeUtils.getAttributeValue(ent, "epicfight:weight") - 40) / (AttributeUtils.getAttributeValue(ent, "pride:max_weight"))) * 100);
        }
        return load;
    }

    public static int getTotalLevel(CompoundTag tag) {
        return (tag.getInt("StrengthLvl") + tag.getInt("DexterityLvl") + tag.getInt("VigorLvl") + tag.getInt("EnduranceLvl") + tag.getInt("MindLvl")) / 5;
    }

    public static void addXp(Player player, String stat, int amount) {
        if (player != null) {
            CompoundTag variables = player.getPersistentData();
            int[] newvariables;
            switch (stat) {
                case "Strength" -> {
                    newvariables = addXpBase(variables.getInt("StrengthLvl"), variables.getInt("StrengthXp"), variables.getInt("StrengthMaxXp"), amount);
                    variables.putInt("StrengthLvl", newvariables[0]);
                    variables.putInt("StrengthXp", newvariables[1]);
                    variables.putInt("StrengthMaxXp", newvariables[2]);
                }
                case "Dexterity" -> {
                    newvariables = addXpBase(variables.getInt("DexterityLvl"), variables.getInt("DexterityXp"), variables.getInt("DexterityMaxXp"), amount);
                    variables.putInt("DexterityLvl", newvariables[0]);
                    variables.putInt("DexterityXp", newvariables[1]);
                    variables.putInt("DexterityMaxXp", newvariables[2]);
                }
                case "Vigor" -> {
                    newvariables = addXpBase(variables.getInt("VigorLvl"), variables.getInt("VigorXp"), variables.getInt("VigorMaxXp"), amount);
                    variables.putInt("VigorLvl", newvariables[0]);
                    variables.putInt("VigorXp", newvariables[1]);
                    variables.putInt("VigorMaxXp", newvariables[2]);
                }
                case "Endurance" -> {
                    newvariables = addXpBase(variables.getInt("EnduranceLvl"), variables.getInt("EnduranceXp"), variables.getInt("EnduranceMaxXp"), amount);
                    variables.putInt("EnduranceLvl", newvariables[0]);
                    variables.putInt("EnduranceXp", newvariables[1]);
                    variables.putInt("EnduranceMaxXp", newvariables[2]);
                }
                case "Mind" -> {
                    newvariables = addXpBase(variables.getInt("MindLvl"), variables.getInt("MindXp"), variables.getInt("MindMaxXp"), amount);
                    variables.putInt("MindLvl", newvariables[0]);
                    variables.putInt("MindXp", newvariables[1]);
                    variables.putInt("MindMaxXp", newvariables[2]);
                }
            }
        }
    }

    public static int[] addXpBase(int lvl, int xp, int maxxp, int amount) {
        xp = xp + amount;
        if (xp >= maxxp) {
            xp = xp - maxxp;
            lvl = lvl + 1;
            maxxp = setMaxXp(lvl);
            if (lvl >= 100) {
                lvl = 100;
                xp = 0;
                maxxp = 0;
            }
        }
        return new int[]{lvl, xp, maxxp};
    }

    public static int setMaxXp(int lvl) {
        return (int) Math.round(100 * Math.pow(1.05, (lvl - 1)));
    }
}
