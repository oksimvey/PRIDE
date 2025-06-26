package com.robson.pride.api.utils;

import com.robson.pride.api.data.types.WeaponData;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.progression.PlayerAttributeSetup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static com.robson.pride.api.data.player.ClientDataManager.CLIENT_DATA_MANAGER;

public class ProgressionUtils {

    public static int getLoadPercentage(LivingEntity ent) {
        int load = 0;
        if (ent != null) {
            load = Math.round(((AttributeUtils.getAttributeValue(ent, "epicfight:weight") - 40) / (AttributeUtils.getAttributeValue(ent, "pride:max_weight"))) * 100);
        }
        return load;
    }

    public static boolean hasSkill(Entity ent, String skill) {
        if (ent != null) {
            if (ent instanceof Player) {

            }
        }
        return false;
    }

    public static int getTotalLevel(Entity ent) {
        if (ent != null) {
            if (ent instanceof Player player) {
                return CLIENT_DATA_MANAGER.get(player).getProgressionData().getTotalLevel();
           }
        }
        return 0;
    }

    public static void addXp(Player player, String stat, int amount) {
        if (player != null) {
            CompoundTag variables = player.getPersistentData();
            int[] newvariables;
            switch (stat) {
                case "Strength" -> {
                    newvariables = addXpBase(variables.getInt("StrengthLvl"), variables.getInt("StrengthXp"), variables.getInt("StrengthMaxXp"), amount);
                    if (newvariables[0] != variables.getInt("StrengthLvl")) {
                        variables.putInt("StrengthLvl", newvariables[0]);
                        variables.putInt("StrengthMaxXp", newvariables[2]);
                    }
                    variables.putInt("StrengthXp", newvariables[1]);
                }
                case "Dexterity" -> {
                    newvariables = addXpBase(variables.getInt("DexterityLvl"), variables.getInt("DexterityXp"), variables.getInt("DexterityMaxXp"), amount);
                    if (newvariables[0] != variables.getInt("DexterityLvl")) {
                        PlayerAttributeSetup.setupDexterity(player, newvariables[0]);
                        variables.putInt("DexterityLvl", newvariables[0]);
                        variables.putInt("DexterityMaxXp", newvariables[2]);
                    }
                    variables.putInt("DexterityXp", newvariables[1]);
                }
                case "Vigor" -> {
                    newvariables = addXpBase(variables.getInt("VigorLvl"), variables.getInt("VigorXp"), variables.getInt("VigorMaxXp"), amount);
                    if (newvariables[0] != variables.getInt("VigorLvl")) {
                        PlayerAttributeSetup.setupVigor(player, newvariables[0]);
                        variables.putInt("VigorLvl", newvariables[0]);
                        variables.putInt("VigorMaxXp", newvariables[2]);
                    }
                    variables.putInt("VigorXp", newvariables[1]);
                }
                case "Endurance" -> {
                    newvariables = addXpBase(variables.getInt("EnduranceLvl"), variables.getInt("EnduranceXp"), variables.getInt("EnduranceMaxXp"), amount);
                    if (newvariables[0] != variables.getInt("EnduranceLvl")) {
                        PlayerAttributeSetup.setupEndurance(player, newvariables[0]);
                        variables.putInt("EnduranceLvl", newvariables[0]);
                        variables.putInt("EnduranceMaxXp", newvariables[2]);
                    }
                    variables.putInt("EnduranceXp", newvariables[1]);
                }
                case "Mind" -> {
                    newvariables = addXpBase(variables.getInt("MindLvl"), variables.getInt("MindXp"), variables.getInt("MindMaxXp"), amount);
                    if (newvariables[0] != variables.getInt("MindLvl")) {
                        PlayerAttributeSetup.setupMind(player, newvariables[0]);
                        variables.putInt("MindLvl", newvariables[0]);
                        variables.putInt("MindMaxXp", newvariables[2]);
                    }
                    variables.putInt("MindXp", newvariables[1]);
                }
            }
        }
    }

    public static boolean haveReqs(Player player) {
        if (player != null) {
            ItemStack weapon = player.getMainHandItem();
            WeaponData data = ServerDataManager.getWeaponData(weapon);
            if (data != null) {
                WeaponData.AttributeReqs reqs = data.getAttributeReqs();
                boolean mindreqs = true;
                boolean strreqs = true;
                boolean dexreqs = true;
                if (reqs.requiredStrength() != 0) {
                    strreqs = reqs.requiredStrength() <= player.getPersistentData().getInt("StrengthLvl");
                }
                if (reqs.requiredDexterity() != 0) {
                    dexreqs = reqs.requiredDexterity() <= player.getPersistentData().getInt("DexterityLvl");
                }
                if (weapon.getTag().contains("requiredMind")) {
                    mindreqs = weapon.getTag().getDouble("requiredMind") <= player.getPersistentData().getInt("MindLvl");
                } else if (reqs.requiredMind() != 0) {
                    mindreqs = reqs.requiredMind() <= player.getPersistentData().getInt("MindLvl");
                }
                return mindreqs && strreqs && dexreqs;
            }
        }
        return false;
    }

    public static int[] addXpBase(int lvl, int xp, int maxxp, int amount) {
        xp = xp + amount;
        while (xp >= maxxp) {
            xp = xp - maxxp;
            lvl = lvl + 1;
            maxxp = setMaxXp(lvl);
            if (lvl >= 100) {
                lvl = 100;
                xp = 0;
                maxxp = 100;
            }
        }
        return new int[]{lvl, xp, maxxp};
    }

    public static int setMaxXp(int lvl) {
        return (int) Math.round(100 * Math.pow(1.05, (lvl - 1)));
    }
}
