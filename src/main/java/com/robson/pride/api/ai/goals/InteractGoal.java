package com.robson.pride.api.ai.goals;

import com.robson.pride.api.npc.JsonDialoguesReader;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class InteractGoal extends net.minecraft.world.entity.ai.goal.Goal {
    private final Mob entity;
    private final Class<? extends LivingEntity> targetClass;
    private final double speed;
    private final double executeDistance;
    private final int maxCooldown;
    private final double chance;
    private LivingEntity target;
    private int cooldownTicks = 0;

    public InteractGoal(Mob entity, Class<? extends LivingEntity> targetClass, double speed, double executeDistance, int maxCooldown, double chance) {
        this.entity = entity;
        this.targetClass = targetClass;
        this.speed = speed;
        this.executeDistance = executeDistance;
        this.maxCooldown = maxCooldown;
        this.chance = chance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE)); // Define que este Goal controla movimento
    }

    @Override
    public boolean canUse() {

        if (cooldownTicks > 0) {
            cooldownTicks--;
            return false;
        }

        if (Math.random() > this.chance) {
            return false;
        }

        this.target = this.entity.level().getEntitiesOfClass(targetClass, entity.getBoundingBox().inflate(10.0)).stream()
                .filter(targetCandidate -> targetCandidate != null && targetCandidate.isAlive() && targetCandidate != this.entity)
                .min((e1, e2) -> Double.compare(this.entity.distanceTo(e1), this.entity.distanceTo(e2)))
                .orElse(null);
        return this.target != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.target != null && this.target.isAlive() && this.entity.distanceTo(this.target) > executeDistance;
    }

    @Override
    public void tick() {
        if (this.target != null) {
            this.entity.getNavigation().moveTo(this.target, this.speed);
            if (this.entity.distanceTo(this.target) <= executeDistance) {
                if (JsonDialoguesReader.isSpeaking.get(this.target) == null && JsonDialoguesReader.isSpeaking.get(this.entity) == null) {
                    JsonDialoguesReader.onInteraction(this.target, this.entity);
                    this.cooldownTicks = this.maxCooldown;
                }
            }
        }
    }

    @Override
    public void stop() {
        this.target = null;
        this.entity.getNavigation().stop();
    }

}
