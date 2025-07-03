package com.robson.pride.api.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public class FollowEntityGoal extends Goal {
    private final PathfinderMob mob;
    private final LivingEntity target;
    private final float speed;
    private final boolean temporary;

    public FollowEntityGoal(PathfinderMob mob, Player target, float speed, boolean temporary) {
        this.mob = mob;
        this.target = target;
        this.speed = speed;
        this.temporary = temporary;
    }

    @Override
    public boolean canUse() {
        return target != null && !mob.isPassenger();
    }

    @Override
    public void tick() {
        if (LodTick.canTick(mob, 1)) {
            mob.getNavigation().moveTo(target, speed);
            if (mob.distanceToSqr(target) < 4) {
                mob.getNavigation().stop();
                if (temporary) {
                    mob.goalSelector.removeGoal(this);
                }
            }
        }
    }
}
