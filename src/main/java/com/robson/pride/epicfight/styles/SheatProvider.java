package com.robson.pride.epicfight.styles;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.ArrayList;
import java.util.List;

public class SheatProvider {

    private static byte idlecounter = 0;

    public static List<Entity> sheatentities = new ArrayList<>();

    public static void provideSheat(LocalPlayer player){
        LocalPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, LocalPlayerPatch.class);
        if (playerPatch != null){
            if (playerPatch.getClientAnimator().currentMotion() == LivingMotions.IDLE){
                idlecounter++;
                if (idlecounter == 40){
                    sheatentities.add(player);
                }
            }
            else {
                idlecounter = 0;
                sheatentities.remove(player);
            }
        }
    }
}
