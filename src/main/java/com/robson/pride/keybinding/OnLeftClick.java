package com.robson.pride.keybinding;

import com.robson.pride.api.mechanics.Stealth;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.KeyRegister;
import com.robson.pride.skills.special.AirSlamSkill;
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
                                                if (player.getPersistentData().getBoolean("mikiri_sweep")){
                                                    AirSlamSkill as = new AirSlamSkill();
                                                    as.tryToExecute(player);
                                                }
                                               else SkillCore.onSkillExecute(player);
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
            if (Stealth.canBackStab(ent, target)){
                target.getPersistentData().putBoolean("isVulnerable", true);
            }
        }
        TimerUtil.schedule(() -> {
            if (target.getPersistentData().getBoolean("isVulnerable")) {
                AnimUtils.playAnim(ent, AnimationsRegister.EXECUTE, 0);
            }
        }, 5, TimeUnit.MILLISECONDS);
    }
}
