package com.robson.pride.keybinding;

import com.robson.pride.api.utils.*;
import com.robson.pride.mechanics.MikiriCounter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class onSpacePress {
    public static void onPress(Player player){
        Entity target = TargetUtil.getTarget(player);
        if (target != null){
            if (Objects.equals(target.getPersistentData().getString("Perilous"), "sweep") && Objects.equals(player.getPersistentData().getString("Mikiri"), "Jump")){
                MikiriCounter.onSweepMikiri(player, target);
            }
        }
    }
}
