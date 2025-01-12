package com.robson.pride.api.utils;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
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
                        AHPatch.setAttackSpeed(convert);
                        AHPatch.resetMotion();
                        AHPatch.playAnimationSynchronized(animation, convert, SPPlayAnimation::new);
                    } else livingEntityPatch.playAnimationSynchronized(animation, convert, SPPlayAnimation::new);
                }
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

    public static InteractionHand getAttackingHand(Entity ent){
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

    public static void playAnimchangingBox(Entity ent, String anim, float convert, float width, float height, int duration){
        TimerUtil.schedule(() -> {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                StaticAnimation animation = AnimationManager.getInstance().byKeyOrThrow(anim);
                if (animation != null) {
                    resizeBoundingBox(animation, width, height);
                    TimerUtil.schedule(()-> restoreBoundingBox(animation), duration, TimeUnit.MILLISECONDS);
                    if (livingEntityPatch instanceof AdvancedCustomHumanoidMobPatch<?> AHPatch) {
                        AHPatch.setBlocking(false);
                        AHPatch.setParry(false);
                        AHPatch.setAttackSpeed(convert);
                        AHPatch.resetMotion();
                        AHPatch.playAnimationSynchronized(animation, convert, SPPlayAnimation::new);
                    } else livingEntityPatch.playAnimationSynchronized(animation, convert, SPPlayAnimation::new);
                }
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

    public static void resizeBoundingBox(StaticAnimation animation, float width, float height) {
        animation.addEvents(
                AnimationProperty.StaticAnimationProperty.EVENTS,
                AnimationEvent.create(Animations.ReusableSources.RESIZE_BOUNDING_BOX, AnimationEvent.Side.BOTH)
                        .params(EntityDimensions.scalable(width, height))
        );
    }

    public static void restoreBoundingBox(StaticAnimation animation) {
        animation.addEvents(
                AnimationProperty.StaticAnimationProperty.ON_END_EVENTS,
                AnimationEvent.create(Animations.ReusableSources.RESTORE_BOUNDING_BOX, AnimationEvent.Side.BOTH)
        );
    }

    public static byte getDodgeType(ServerPlayer player){
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

    public static void preventAttack(Entity ent, int duration){
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null){
            livingent.getEntityState().setState(EntityState.CAN_SKILL_EXECUTION, false);
            TimerUtil.schedule(()->  livingent.getEntityState().setState(EntityState.CAN_SKILL_EXECUTION, true), duration, TimeUnit.MILLISECONDS);
        }
    }

    public static void applyStun(Entity ent, StunType stuntype , float duration){
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null) {
            livingent.applyStun(stuntype, duration);
        }
    }


    public static boolean checkAttack(Entity ent){
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null){
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

    public static void cancelMotion(Entity ent){
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null){
            livingEntityPatch.cancelAnyAction();
        }
    }
}