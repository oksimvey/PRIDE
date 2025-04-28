package com.robson.pride.api.client;

import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.gui.screen.config.EpicFightOptionSubScreen;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.config.ClientConfig;
import yesman.epicfight.config.ConfigManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

public class AutoBattleMode {

    public static void autoSwitch(LocalPlayer player){
        ClientConfig config = ConfigManager.INGAME_CONFIG;

                if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND))
        ;}
    }
