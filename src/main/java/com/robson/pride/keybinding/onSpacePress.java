package com.robson.pride.keybinding;

import com.robson.pride.api.utils.*;
import com.robson.pride.mechanics.MikiriCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class onSpacePress {
    public static void onPress(Player player){
        Entity target = TargetUtil.getTarget(player);
        if (target != null){
            if (Objects.equals(target.getPersistentData().getString("Perilous"), "Sweep") && Objects.equals(player.getPersistentData().getString("Mikiri"), "Jump")){
                MikiriCounter.onSweepMikiri(player, target);
            }
        }
    }
}
