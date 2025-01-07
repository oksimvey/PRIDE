package com.robson.pride.keybinding;

import com.robson.pride.api.mechanics.Guard;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class onRClick {

    public static void RClick(ServerPlayer player){
        if (player != null){
            if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND)){

            }
        }
    }
}
