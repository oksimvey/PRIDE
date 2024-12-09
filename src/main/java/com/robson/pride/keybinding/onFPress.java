package com.robson.pride.keybinding;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class onFPress {

    public static void swapHand(Player player){
        PlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (playerPatch != null){
            if(playerPatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated()){
                playerPatch.getSkill(SkillSlots.WEAPON_INNATE).deactivate();
            }
            else  {playerPatch.getSkill(SkillSlots.WEAPON_INNATE).activate();}
            playerPatch.updateHeldItem(playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND), playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND), player.getMainHandItem(),player.getMainHandItem(), InteractionHand.MAIN_HAND );

        }
    }
}
