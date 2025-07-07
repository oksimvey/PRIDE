package com.robson.pride.api.data.types.entity;

import com.robson.pride.api.ai.actions.builder.ActionsBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Map;

public class HumanoidMobDataPreset extends MobData {

    protected HumanoidMobDataPreset(ResourceLocation texture, ActionsBuilder combatActions, Map<EquipmentSlot, ItemStack> equipment) {
        super(texture, combatActions, equipment, true, Armatures.BIPED.get(),
                Map.ofEntries(
                        Map.entry(StunType.LONG, Animations.BIPED_HIT_LONG),
                        Map.entry(StunType.HOLD, Animations.BIPED_HIT_SHORT),
                        Map.entry(StunType.SHORT, Animations.BIPED_HIT_SHORT),
                        Map.entry(StunType.KNOCKDOWN, Animations.BIPED_KNOCKDOWN),
                        Map.entry(StunType.FALL, Animations.BIPED_LANDING),
                        Map.entry(StunType.NEUTRALIZE, Animations.BIPED_COMMON_NEUTRALIZED)
                ));
    }



    @Override
    public void initAnimator(Animator animator) {
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.FALL, Animations.BIPED_FALL);
        animator.addLivingAnimation(LivingMotions.MOUNT, Animations.BIPED_MOUNT);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
        animator.addLivingAnimation(LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
    }
}
