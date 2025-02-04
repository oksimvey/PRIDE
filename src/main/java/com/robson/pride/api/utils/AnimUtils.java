package com.robson.pride.api.utils;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.concurrent.TimeUnit;

public class AnimUtils {

    public static void playAnim(Entity ent, StaticAnimation animation, float convert) {
        TimerUtil.schedule(() -> {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                if (animation != null) {
                    if (livingEntityPatch instanceof AdvancedCustomHumanoidMobPatch<?> AHPatch) {
                        AHPatch.setBlocking(false);
                        AHPatch.setParry(false);
                        AHPatch.resetMotion();
                       AHPatch.playAnimationSynchronized(animation, convert, SPPlayAnimation::new);
                    }
                    else livingEntityPatch.playAnimationSynchronized(animation, convert, SPPlayAnimation::new);
                }
            }
        }, 10, TimeUnit.MILLISECONDS);
    }


    public static StaticAnimation addPerilousToAnim(StaticAnimation animation, String perilous) {
        return animation.addEvents(AnimationEvent.TimeStampedEvent.create(0, (entitypatch, self, params) -> {
                    if (entitypatch.getOriginal() != null) {
                        CommandUtils.executeonEntity(entitypatch.getOriginal(),   "say start");
                        entitypatch.getOriginal().getPersistentData().putString("Perilous", perilous);
                    }
                }, AnimationEvent.Side.SERVER),
                AnimationEvent.TimeStampedEvent.create(animation.getTotalTime(), (entitypatch, self, params) -> {
                    if (entitypatch.getOriginal() != null) {
                        CommandUtils.executeonEntity(entitypatch.getOriginal(),   "say end");
                        entitypatch.getOriginal().getPersistentData().remove("Perilous");
                    }
                }, AnimationEvent.Side.SERVER));
    }

    public static StaticAnimation getCurrentAnimation(Entity ent){
        if (ent != null){
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null){
                DynamicAnimation var5 = livingEntityPatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                if (var5 instanceof StaticAnimation animation) {
                    return animation;
                }
            }
        }
        return null;
    }

    public static float getCurrentAnimationDuration(Entity ent){
        if (ent != null){
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null){
                DynamicAnimation var5 = livingEntityPatch.getAnimator().getPlayerFor((DynamicAnimation)null).getAnimation();
                if (var5 != null) {
                    return var5.getTotalTime();
                }
            }
        }
        return 0;
    }

    public static InteractionHand getAttackingHand(Entity ent) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            return livingEntityPatch.getAttackingHand();
        }
        return null;
    }

    public static void playAnimByString(Entity ent, String anim, float convert) {
        StaticAnimation animation = AnimationManager.getInstance().byKeyOrThrow(anim);
        playAnim(ent, animation, convert);
    }


    public static void resizeBoundingBox(Entity ent, float width, float height) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null){
                livingEntityPatch.resetSize(new EntityDimensions(width, height, true));
            }
        }
    }

    public static byte getDodgeType(Player player) {
        if (Minecraft.getInstance().options.keyUp.isDown()) {
            return 1;
        }
        if (Minecraft.getInstance().options.keyLeft.isDown()) {
            return 2;
        }
        if (Minecraft.getInstance().options.keyRight.isDown()) {
            return 3;
        }
        return 4;
    }

    public static void preventAttack(Entity ent, int duration) {
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null) {
            livingent.getEntityState().setState(EntityState.CAN_SKILL_EXECUTION, false);
            TimerUtil.schedule(() -> livingent.getEntityState().setState(EntityState.CAN_SKILL_EXECUTION, true), duration, TimeUnit.MILLISECONDS);
        }
    }

    public static void applyStun(Entity ent, StunType stuntype, float duration) {
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null) {
            if (livingent instanceof AdvancedCustomHumanoidMobPatch<?> mobPatch){
                mobPatch.applyStun(stuntype, duration);
                return;
            }
            livingent.applyStun(stuntype, duration);
        }
    }


    public static boolean checkAttack(Entity ent) {
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null) {
            return livingent.getEntityState().attacking();
        }
        return false;
    }

    public static void rotateToEntity(Entity ent1, Entity ent2) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent1, LivingEntityPatch.class);
        if (livingEntityPatch != null && ent2 != null) {
            livingEntityPatch.rotateTo(ent2, 1000, false);
            livingEntityPatch.correctRotation();
        }
    }

    public static void cancelMotion(Entity ent) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            if (livingEntityPatch instanceof AdvancedCustomHumanoidMobPatch<?> mobPatch){
                mobPatch.resetMotion();
            }
            livingEntityPatch.cancelAnyAction();
        }
    }
}