package com.robson.pride.api.ai.goals;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class PrideChasingGoal<T extends AdvancedCustomHumanoidMobPatch<?>> extends MeleeAttackGoal {
    private final T mobpatch;
    protected final double attackRadiusSqr;
    private final double speed;

    public PrideChasingGoal(T mobpatch, PathfinderMob pathfinderMob, double speedModifier, boolean longMemory, double attackRadius) {
        super(pathfinderMob, speedModifier, longMemory);
        this.mobpatch = mobpatch;
        this.speed = speedModifier;
        this.attackRadiusSqr = attackRadius * attackRadius;
    }

    public boolean canUse() {
        return !this.mobpatch.getEntityState().movementLocked() && (this.mobpatch.getStrafingTime() > 0 || super.canUse());
    }

    public void tick() {
        if (this.mobpatch.getInactionTime() > 0) {
            this.mobpatch.setInactionTime(this.mobpatch.getInactionTime() - 1);
        }
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            if (this.mob instanceof PrideMobBase prideMobBase){
                prideMobBase.deserializePassiveSkills();
            }
            if (!this.mobpatch.getEntityState().turningLocked()) {
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            if (!this.mobpatch.getEntityState().movementLocked()) {
                boolean withDistance = this.attackRadiusSqr > this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
                if (this.mobpatch.getStrafingTime() > 0) {
                    this.mobpatch.setStrafingTime(this.mobpatch.getStrafingTime() - 1);
                    this.mob.getNavigation().stop();
                    this.mob.lookAt(target, 30.0F, 30.0F);
                    this.mob.getMoveControl().strafe(withDistance && this.mobpatch.getStrafingForward() > 0.0F ? 0.0F : this.mobpatch.getStrafingForward(), this.mobpatch.getStrafingClockwise());
                } else if (withDistance) {
                    this.mob.getNavigation().stop();
                } else if (this.mobpatch.isBlocking()) {
                    this.mob.lookAt(target, 30.0F, 30.0F);
                    this.mob.getNavigation().moveTo(target, this.speed * (double)0.8F);
                } else {
                    super.tick();
                }

            }
        }
    }

    public void stop() {
        super.stop();
    }

    protected void checkAndPerformAttack(LivingEntity target, double p_25558_) {
    }

    protected double getAttackReachSqr(LivingEntity p_25556_) {
        return this.attackRadiusSqr;
    }
}
