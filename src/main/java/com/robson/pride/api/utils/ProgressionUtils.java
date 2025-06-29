package com.robson.pride.api.utils;

import com.robson.pride.api.data.player.ClientData;
import com.robson.pride.api.data.player.ClientSavedData;
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

    public static byte getStatLvl(Player player, byte stat) {
        if (player != null){
            ClientData data = CLIENT_DATA_MANAGER.get(player);
            if (data != null){
                return data.getProgressionData().getLevel(stat);
            }
        }
        return 0;
    }

    public static void addXp(Player player, byte stat, int amount) {
        if (player != null) {
            ClientData data = CLIENT_DATA_MANAGER.get(player);
            if (data != null){
                data.getProgressionData().addXP(stat, amount);
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
                    strreqs = reqs.requiredStrength() <= getStatLvl(player, ClientSavedData.Strength);
                }
                if (reqs.requiredDexterity() != 0) {
                    dexreqs = reqs.requiredDexterity() <= getStatLvl(player, ClientSavedData.Dexterity);
                }
                if (weapon.getTag().contains("requiredMind")) {
                    mindreqs = weapon.getTag().getDouble("requiredMind") <= getStatLvl(player, ClientSavedData.Mind);
                } else if (reqs.requiredMind() != 0) {
                    mindreqs = reqs.requiredMind() <= getStatLvl(player, ClientSavedData.Mind);
                }
                return mindreqs && strreqs && dexreqs;
            }
        }
        return false;
    }

}
