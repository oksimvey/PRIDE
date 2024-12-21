package com.robson.pride.entities.goals;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class TargetWithEffectGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final Mob mob;
    private final MobEffect effect;

    public TargetWithEffectGoal(Mob mob, Class<T> targetClass, boolean mustSee, MobEffect effect) {
        super(mob, targetClass, mustSee);
        this.mob = mob;
        this.effect = effect;
    }

    @Override
    public boolean canUse() {

        if (this.mob.getTarget() != null) {
            LivingEntity target = this.mob.getTarget();
            MobEffectInstance effectInstance = target.getEffect(this.effect);

            return effectInstance != null;
        }

        this.findTarget();
        if (this.target == null) return false;

        return this.target.getEffect(this.effect) != null;
    }
}