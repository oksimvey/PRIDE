package com.robson.pride.keybinding;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class onFPress {

    public static void swapHand(Player player){
       if (player != null){
           if (player.getMainHandItem().getTag() != null){
               if (!player.getMainHandItem().getTag().getBoolean("two_handed")){
                   player.getMainHandItem().getTag().putBoolean("two_handed", true);
               }
               else  player.getMainHandItem().getTag().putBoolean("two_handed", false);
           }
       }
    }
}
