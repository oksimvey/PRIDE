package com.robson.pride.progression;

import com.robson.pride.api.utils.ElementalUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class NewCap {

    public static void setupVariables(CompoundTag oldtag, CompoundTag newtag) {
        newtag.putInt("StrengthLvl", oldtag.getInt("StrengthLvl"));
        newtag.putInt("DexterityLvl", oldtag.getInt("DexterityLvl"));
        newtag.putInt("VigorLvl", oldtag.getInt("VigorLvl"));
        newtag.putInt("EnduranceLvl", oldtag.getInt("EnduranceLvl"));
        newtag.putInt("MindLvl", oldtag.getInt("MindLvl"));
        newtag.putInt("StrengthXp", oldtag.getInt("StrengthXp"));
        newtag.putInt("DexterityXp", oldtag.getInt("DexterityXp"));
        newtag.putInt("VigorXp", oldtag.getInt("VigorXp"));
        newtag.putInt("EnduranceXp", oldtag.getInt("EnduranceXp"));
        newtag.putInt("MindXp", oldtag.getInt("MindXp"));
        newtag.putInt("StrengthMaxXp", oldtag.getInt("StrengthMaxXp"));
        newtag.putInt("DexterityMaxXp", oldtag.getInt("DexterityMaxXp"));
        newtag.putInt("VigorMaxXp", oldtag.getInt("VigorMaxXp"));
        newtag.putInt("EnduranceMaxXp", oldtag.getInt("EnduranceMaxXp"));
        newtag.putInt("MindMaxXp", oldtag.getInt("MindMaxXp"));
        newtag.putString("Element", oldtag.getString("Element"));
    }

    public static void startVariables(Player player, CompoundTag newtag) {
        newtag.putInt("StrengthLvl", 1);
        newtag.putInt("DexterityLvl", 1);
        newtag.putInt("VigorLvl", 1);
        newtag.putInt("EnduranceLvl", 1);
        newtag.putInt("MindLvl", 1);
        newtag.putInt("StrengthXp", 0);
        newtag.putInt("DexterityXp", 0);
        newtag.putInt("VigorXp", 0);
        newtag.putInt("EnduranceXp", 0);
        newtag.putInt("MindXp", 0);
        newtag.putInt("StrengthMaxXp", 100);
        newtag.putInt("DexterityMaxXp", 100);
        newtag.putInt("VigorMaxXp", 100);
        newtag.putInt("EnduranceMaxXp", 100);
        newtag.putInt("MindMaxXp", 100);
        ElementalUtils.rollElement(player);
    }

    public static boolean haveVariables(CompoundTag tag) {
        return tag.getInt("StrengthLvl") != 0 &&
                tag.getInt("DexterityLvl") != 0 &&
                tag.getInt("VigorLvl") != 0 &&
                tag.getInt("EnduranceLvl") != 0 &&
                tag.getInt("MindLvl") != 0 &&
                tag.getInt("StrengthMaxXp") != 0 &&
                tag.getInt("DexterityMaxXp") != 0 &&
                tag.getInt("VigorMaxXp") != 0 &&
                tag.getInt("EnduranceMaxXp") != 0 &&
                tag.getInt("MindMaxXp") != 0 &&
                hasElement(tag.getString("Element"));
    }

    public static boolean hasElement(String element) {
        return element.equals("Darkness") ||
                element.equals("Light") ||
                element.equals("Thunder") ||
                element.equals("Sun") ||
                element.equals("Moon") ||
                element.equals("Blood") ||
                element.equals("Wind") ||
                element.equals("Nature") ||
                element.equals("Ice") ||
                element.equals("Water");
    }
}
