package com.robson.pride.keybinding;

import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

public class onRClick {

    public static void RClick(ServerPlayer player){
        if (player != null){
            if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND)){

            }
        }
    }
}
