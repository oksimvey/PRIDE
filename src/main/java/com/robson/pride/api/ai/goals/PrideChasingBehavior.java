package com.robson.pride.api.ai.goals;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;

import java.util.concurrent.TimeUnit;

public class PrideChasingBehavior<T extends AdvancedCustomHumanoidMobPatch<?>> extends MoveToTargetSink {
    private final T mobpatch;
    protected final double attackRadiusSqr;
    private final double speed;
    private LivingEntity target;

    public PrideChasingBehavior(T mobpatch, double speedModifier, double attackRadius) {
        super(150, 250);
        this.mobpatch = mobpatch;
        this.speed = speedModifier;
        this.attackRadiusSqr = attackRadius * attackRadius;
    }

    public boolean checkExtraStartConditions(ServerLevel level, Mob mob) {
        return this.mobpatch.getStrafingTime() > 0 || super.checkExtraStartConditions(level, mob);
    }

    public void tick(ServerLevel level, Mob mob, long p_23619_) {
        if (this.mobpatch.getInactionTime() > 0) {
            this.mobpatch.setInactionTime(this.mobpatch.getInactionTime() - 1);
        }

        LivingEntity target = mob.getTarget();
        if (target != null) {
            if (!this.mobpatch.getEntityState().turningLocked()) {
                mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
            if (!this.mobpatch.getEntityState().movementLocked()) {
                boolean withDistance = this.attackRadiusSqr > mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
                if (this.mobpatch.getStrafingTime() > 0) {
                    this.mobpatch.setStrafingTime(this.mobpatch.getStrafingTime() - 1);
                    mob.getNavigation().stop();
                    mob.lookAt(target, 30.0F, 30.0F);
                    mob.getMoveControl().strafe(withDistance && this.mobpatch.getStrafingForward() > 0.0F ? 0.0F : this.mobpatch.getStrafingForward(), this.mobpatch.getStrafingClockwise());
                } else if (withDistance) {
                    mob.getNavigation().stop();
                } else if (this.mobpatch.isBlocking()) {
                    mob.lookAt(target, 30.0F, 30.0F);
                    mob.getNavigation().moveTo(target, this.speed * (double)0.8F);
                } else {
                    super.tick(level, mob, p_23619_);
                }
            }
        }
    }
}
