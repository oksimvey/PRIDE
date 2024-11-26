package com.robson.pride.keybinding;

import com.robson.pride.api.utils.*;
import net.minecraft.world.entity.Entity;

import java.util.concurrent.TimeUnit;

public class OnLeftClick {

    public static void onLClick(Entity ent){
        Entity target = TargetUtil.getTarget(ent);
        if (target != null){
            onTarget(ent, target);
        }
    }
    public static void onTarget(Entity ent, Entity target) {
        if (TargetUtil.getTarget(target) == null){
            target.getPersistentData().putBoolean("isVulnerable", true);
        }
        TimerUtil.schedule(()-> {
            if (target.getPersistentData().getBoolean("isVulnerable")) {
                CutsceneUtils.executionCutscene(ent);
                AnimUtils.playAnim(ent, "epicfight:biped/combat/tachi_dash", 0);
            }
        }, 5, TimeUnit.MILLISECONDS);
    }
}
