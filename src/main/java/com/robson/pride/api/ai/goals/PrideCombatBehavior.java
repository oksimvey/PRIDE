package com.robson.pride.api.ai.goals;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

public class PrideCombatBehavior<T extends MobPatch<?>> extends Behavior<Mob> {

    protected final T mobpatch;

    protected final CombatBehaviors<T> combatBehaviors;

    public PrideCombatBehavior(T mobpatch, CombatBehaviors<T> combatBehaviors) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT));
        this.mobpatch = mobpatch;
        this.combatBehaviors = combatBehaviors;
    }

    protected boolean checkExtraStartConditions(ServerLevel levelIn, Mob entityIn) {
        return this.isValidTarget(this.mobpatch.getTarget());
    }

    protected boolean canStillUse(ServerLevel levelIn, Mob entityIn, long gameTimeIn) {
        return this.checkExtraStartConditions(levelIn, entityIn) && BehaviorUtils.canSee(entityIn, this.mobpatch.getTarget()) && !this.mobpatch.getEntityState().hurt();
    }

    protected boolean isValidTarget(LivingEntity attackTarget) {
        return attackTarget != null && attackTarget.isAlive() && (!(attackTarget instanceof Player) || !attackTarget.isSpectator() && !((Player) attackTarget).isCreative());
    }
}