package com.robson.pride.keybinding;

import com.robson.pride.api.mechanics.Stealth;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.CutsceneUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.KeyRegister;
import com.robson.pride.skills.special.AirSlamSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.concurrent.TimeUnit;

public class OnLeftClick {

    public static void onLClick(Player player) {
        Entity target = TargetUtil.getTarget(player);
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
                                                if (player.getPersistentData().getBoolean("mikiri_sweep")) {
                                                    AirSlamSkill as = new AirSlamSkill();
                                                    as.tryToExecute(player);
                                                } else SkillCore.onSkillExecute(player);
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
                target.getPersistentData().putBoolean("isVulnerable", true);
            }
        }
        TimerUtil.schedule(() -> {
            if (target.getPersistentData().getBoolean("isVulnerable")) {
                AnimUtils.playAnim(ent, AnimationsRegister.EXECUTE, 0);
                LocalPlayer player = Minecraft.getInstance().player;
            }
        }, 5, TimeUnit.MILLISECONDS);
    }
}
