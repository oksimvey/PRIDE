package com.robson.pride.progression;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class PlayerAttributeSetup {

    public static void setupPlayerAttributes(Player player){
        if (player != null){
            int strengthlvl = player.getPersistentData().getInt("StrengthLvl");
            int dexteritylvl = player.getPersistentData().getInt("DexterityLvl");
            int vigorlvl = player.getPersistentData().getInt("VigorLvl");
            int endurancelvl = player.getPersistentData().getInt("EnduranceLvl");
            int mindlvl = player.getPersistentData().getInt("MindLvl");
           setupVigor(player, vigorlvl);
        }
    }

    public static void setupStrength(Player player, int strengthlvl){
        if (player != null){

        }
    }

    public static void setupDexterity(Player player, int dexteritylvl){
        if (player != null){

        }
    }

    public static void setupVigor(Player player, int vigorlvl){
        if (player != null){
            player.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(20 + (vigorlvl * 1.8));
        }
    }

    public static void setupEndurance(Player player, int endurancelvl){
        if (player != null){

        }
    }

    public static void setupMind(Player player, int mindlvl){
        if (player != null){

        }
    }
}
