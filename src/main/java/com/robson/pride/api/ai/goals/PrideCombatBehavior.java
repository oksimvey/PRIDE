package com.robson.pride.api.ai.goals;

import com.nameless.indestructible.mixin.CombatBehaviorsMixin;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.utils.CommandUtils;
import net.minecraft.client.Minecraft;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;

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

    protected void tick(ServerLevel worldIn, Mob entityIn, long gameTimeIn) {
        MobPatch mobPatch = this.mobpatch;
        if (mobPatch instanceof AdvancedCustomHumanoidMobPatch<?> ACHMobpatch) {
            boolean inaction = ACHMobpatch.isBlocking() || ACHMobpatch.getInactionTime() > 0;
            if (this.mobpatch.getTarget() != null) {
                if (mobPatch.getOriginal() instanceof PrideMobBase prideMobBase && prideMobBase.canTickLod(Minecraft.getInstance())) CommandUtils.executeonEntity(prideMobBase, "say top");
                EntityState state = this.mobpatch.getEntityState();
                this.combatBehaviors.tick();
                if (this.combatBehaviors.hasActivatedMove()) {
                    if (state.hurt() && state.hurtLevel() >= ACHMobpatch.getHurtResistLevel()) {
                        ((CombatBehaviorsMixin)this.combatBehaviors).setCurrentBehaviorPointer(-1);
                        return;
                    }
                    if (state.canBasicAttack() && !inaction) {
                        CombatBehaviors.Behavior<T> result = this.combatBehaviors.tryProceed();
                        if (result != null) {
                            ACHMobpatch.resetMotion();
                            result.execute(this.mobpatch);
                        }
                    }
                }
                else if (!state.inaction() && !inaction) {
                    CombatBehaviors.Behavior<T> result = this.combatBehaviors.selectRandomBehaviorSeries();
                    if (result != null) {
                        ACHMobpatch.resetMotion();
                        result.execute(this.mobpatch);
                    }
                }
            }
        }
    }

    protected boolean isValidTarget(LivingEntity attackTarget) {
        return attackTarget != null && attackTarget.isAlive() && (!(attackTarget instanceof Player) || !attackTarget.isSpectator() && !((Player)attackTarget).isCreative());
    }
}