package com.robson.pride.api.client;


import net.minecraft.world.InteractionHand;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class AutoBattleMode {

    public static void autoSwitch(PlayerPatch playerPatch) {
        if (playerPatch != null) {
            if (!playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).isEmpty()) {
                playerPatch.toMode(PlayerPatch.PlayerMode.BATTLE, true);
            } else playerPatch.toMode(PlayerPatch.PlayerMode.MINING, true);
        }
    }
}
