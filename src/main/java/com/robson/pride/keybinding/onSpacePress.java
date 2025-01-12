package com.robson.pride.keybinding;

import com.robson.pride.api.mechanics.MikiriCounter;
import com.robson.pride.api.utils.TargetUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class onSpacePress {
    public static void onPress(Player player){
        Entity target = TargetUtil.getTarget(player);
        if (target != null){
            if (Objects.equals(target.getPersistentData().getString("Perilous"), "sweep") && player.getPersistentData().getBoolean("mikiri_sweep")){
                MikiriCounter.onSweepMikiri(player, target);
            }
        }
    }
}
