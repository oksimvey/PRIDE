package com.robson.pride.keybinding;

import com.robson.pride.api.mechanics.MikiriCounter;
import com.robson.pride.api.mechanics.PerilousAttack;
import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.KeyRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.utils.CameraUtils.correctCamera;

public class onQPress {

    public static void checkDodgeType(Player player) {
        if (Minecraft.getInstance().player != null) {
            correctCamera();
            float EquipLoad = (AttributeUtils.getAttributeValue(player, "epicfight:weight") / AttributeUtils.getAttributeValue(player, "pride:max_weight"));
            if (Minecraft.getInstance().options.keyUp.isDown()) {
                MikiriCounter.setMikiri(player, "Dodge", 0, 350);
            }
            TimerUtil.schedule(() -> {
                if (KeyRegister.keyActionTertiary.isDown()) {
                    TimerUtil.schedule(() -> {
                        if (KeyRegister.keyActionTertiary.isDown()) {
                            TimerUtil.schedule(() -> {
                                if (KeyRegister.keyActionTertiary.isDown()) {
                                    TimerUtil.schedule(() -> {
                                        if (KeyRegister.keyActionTertiary.isDown()) {
                                            TimerUtil.schedule(() -> {
                                                if (KeyRegister.keyActionTertiary.isDown()) {
                                                    onRoll(player);
                                                } else onStep(player, EquipLoad);
                                            }, 50, TimeUnit.MILLISECONDS);
                                        } else onStep(player, EquipLoad);
                                    }, 50, TimeUnit.MILLISECONDS);
                                } else onStep(player, EquipLoad);
                            }, 25, TimeUnit.MILLISECONDS);
                        } else onStep(player, EquipLoad);
                    }, 25, TimeUnit.MILLISECONDS);
                } else onStep(player, EquipLoad);
            }, 25, TimeUnit.MILLISECONDS);
        }
    }

    public static void onStep(Player player, float equipLoad) {
        if (StaminaUtils.getStamina(player) >= 3) {
            player.getPersistentData().putBoolean("isDodging", true);
            TimerUtil.schedule(() -> player.getPersistentData().putBoolean("isDodging", false), 200, TimeUnit.MILLISECONDS);
            StaminaUtils.consumeStamina(player, 3);
            byte dodgetype = AnimUtils.getDodgeType(player);
            String anim = "step_backward";
            if (dodgetype == 1) {
                MikiriCounter.setMikiri(player, "Dodge", 0, 350);
                if (!cantDodgeForward(player)) {
                    anim = "step_forward";
                } else anim = "";
            }
            if (dodgetype == 2) {
                anim = "step_left";
            }
            if (dodgetype == 3) {
                anim = "step_right";
            }
            AnimUtils.playAnimByString(player, "epicfight:biped/skill/" + anim, 0);
            TimerUtil.schedule(() -> {
                Entity target = TargetUtil.getTarget(player);
                if (target != null) {
                    LivingEntityPatch l = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
                    if (l != null) {
                        if (!l.isLastAttackSuccess()) {
                            ProgressionUtils.addXp(player, "Dexterity", 10);
                        }
                    }
                }
            }, 200, TimeUnit.MILLISECONDS);
        }
    }

    public static void onRoll(Player player) {
        if (StaminaUtils.getStamina(player) >= 4) {
            StaminaUtils.consumeStamina(player, 4);
            player.getPersistentData().putBoolean("isDodging", true);
            TimerUtil.schedule(() -> player.getPersistentData().putBoolean("isDodging", false), 300, TimeUnit.MILLISECONDS);
            byte dodgetype = AnimUtils.getDodgeType(player);
            String anim = "epicfight:biped/skill/roll_backward";
            if (dodgetype == 1) {
                anim = "wom:biped/skill/roll_forward";
            }
            if (dodgetype == 2) {
                anim = "wom:biped/skill/roll_left";
            }
            if (dodgetype == 3) {
                anim = "wom:biped/skill/roll_right";
            }
            AnimUtils.playAnimByString(player, anim, 0);
        }
    }

    public static boolean cantDodgeForward(Player player) {
        for (Entity ent : player.level().getEntities(player, MathUtils.createAABBAroundEnt(player, 5))) {
            if (ent != null) {
                if (PerilousAttack.checkPerilous(ent)) {
                    return true;
                } else if (ent instanceof Projectile arrow) {
                    return arrow.getOwner() != player;
                } else if (MikiriCounter.isDodgeCounterableSpell(ent)) {
                    return true;
                }
            }
        }
        return false;
    }
}
