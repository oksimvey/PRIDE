package com.robson.pride.keybinding;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.KeyRegister;
import com.robson.pride.skills.SkillCore;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.events.engine.ControllEngine;

import java.util.concurrent.TimeUnit;

public class OnLeftClick {

    public static void onLClick(Player player) {
        Entity target = TargetUtil.getTarget(player);
        if (target != null ) {
            onTarget(player, target);
        }
        TimerUtil.schedule(()->{
            if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)){
                AnimUtils.preventAttack(player, 3);
                TimerUtil.schedule(()->{
                    if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)){
                        AnimUtils.preventAttack(player, 50);
                        TimerUtil.schedule(()->{
                            if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)){
                                AnimUtils.preventAttack(player, 50);
                                TimerUtil.schedule(()->{
                                    if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)){
                                        AnimUtils.preventAttack(player, 1000);
                                        TimerUtil.schedule(()->{
                                            if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)){
                                               SkillCore.onSkillExecute(player);
                                            }
                                        }, 50, TimeUnit.MILLISECONDS);
                                    }
                                }, 50, TimeUnit.MILLISECONDS);
                            }
                        }, 50, TimeUnit.MILLISECONDS);
                    }
                }, 50, TimeUnit.MILLISECONDS);
            }
        }, 3, TimeUnit.MILLISECONDS);
    }

    public static void onTarget(Entity ent, Entity target) {
        if (target != null) {
        }
        TimerUtil.schedule(() -> {
            if (target.getPersistentData().getBoolean("isVulnerable")) {
                CutsceneUtils.executionCutscene(ent);
                AnimUtils.playAnimByString(ent, "epicfight:biped/combat/tachi_dash", 0);
            }
        }, 5, TimeUnit.MILLISECONDS);
    }
}
