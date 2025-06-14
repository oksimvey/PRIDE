package com.robson.pride.keybinding;

import com.robson.pride.api.mechanics.Stealth;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.CutsceneUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.epicfight.styles.SheatProvider;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.KeyRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.events.engine.ControllEngine;

import java.util.concurrent.TimeUnit;

public class OnLeftClick {

    public static void onLClick(Player player) {
        Entity target = TargetUtil.getTarget(player);
        TimerUtil.schedule(() -> SheatProvider.unsheat(player), 250, TimeUnit.MILLISECONDS);
        if (target != null) {
            onTarget(player, target);
        }
        TimerUtil.schedule(() -> {
            if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)) {
                AnimUtils.preventAttack(player, 3);
                TimerUtil.schedule(() -> {
                    if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)) {
                        AnimUtils.preventAttack(player, 50);
                        TimerUtil.schedule(() -> {
                            if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)) {
                                AnimUtils.preventAttack(player, 50);
                                TimerUtil.schedule(() -> {
                                    if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)) {
                                        AnimUtils.preventAttack(player, 1000);
                                        TimerUtil.schedule(() -> {
                                            if (ControllEngine.isKeyDown(KeyRegister.keyActionSpecial)) {

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

    public static void onTarget(Player ent, Entity target) {
        if (target != null) {
            if (Stealth.canBackStab(ent, target)) {

            }
        }
        TimerUtil.schedule(() -> {
            if (target.getPersistentData().getBoolean("isVulnerable")) {
                AnimUtils.playAnim(ent, AnimationsRegister.EXECUTE, 0);
                LocalPlayer player = Minecraft.getInstance().player;
                CutsceneUtils.executionCutscene(player, target);
            }
        }, 5, TimeUnit.MILLISECONDS);
    }
}
