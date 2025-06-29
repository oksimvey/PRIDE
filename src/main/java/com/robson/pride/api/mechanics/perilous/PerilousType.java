package com.robson.pride.api.mechanics.perilous;

import com.robson.pride.api.utils.AnimUtils;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;

public abstract class PerilousType {

    public abstract boolean canTargetCounter(LivingEntity target);

    public abstract void onCountered(LivingEntity dmgent, LivingEntity target);

    private static boolean commonPierceCondition(LivingEntity target){
        if (target != null){
            StaticAnimation motion = AnimUtils.getCurrentAnimation(target);
            return motion != null && motion == Animations.BIPED_STEP_FORWARD;
        }
        return false;
    }

    public static PerilousType TOTAL = new PerilousType() {
        @Override
        public boolean canTargetCounter(LivingEntity target) {
            return false;
        }
        @Override
        public void onCountered(LivingEntity dmgent, LivingEntity target) {}
    };
}

