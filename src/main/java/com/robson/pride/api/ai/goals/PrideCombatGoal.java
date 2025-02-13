package com.robson.pride.api.ai.goals;

import com.nameless.indestructible.mixin.CombatBehaviorsMixin;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

public class PrideCombatGoal<T extends HumanoidMobPatch<?>> extends AnimatedAttackGoal<T> {

    public PrideCombatGoal(T mobpatch, CombatBehaviors<T> combatBehaviors) {
        super(mobpatch, combatBehaviors);
    }

    public void tick() {
        MobPatch inaction = this.mobpatch;
        if (inaction instanceof AdvancedCustomHumanoidMobPatch<?> ACHMobpatch) {
            boolean inaction1 = ACHMobpatch.isBlocking() || ACHMobpatch.getInactionTime() > 0;
            if (((HumanoidMobPatch)this.mobpatch).getTarget() != null) {
                EntityState state = ((HumanoidMobPatch)this.mobpatch).getEntityState();
                this.combatBehaviors.tick();
                if (this.combatBehaviors.hasActivatedMove()) {
                    if (state.hurt() && state.hurtLevel() >= ACHMobpatch.getHurtResistLevel()) {
                        ((CombatBehaviorsMixin)this.combatBehaviors).setCurrentBehaviorPointer(-1);
                        return;
                    }

                    if (state.canBasicAttack() && !inaction1) {
                        CombatBehaviors.Behavior<T> result = this.combatBehaviors.tryProceed();
                        if (result != null) {
                            ACHMobpatch.resetMotion();
                            result.execute( this.mobpatch);
                        }
                    }
                } else if (!state.inaction() && !inaction1) {
                    CombatBehaviors.Behavior<T> result = this.combatBehaviors.selectRandomBehaviorSeries();
                    if (result != null) {
                        ACHMobpatch.resetMotion();
                        result.execute( this.mobpatch);
                    }
                }
            }

        }
    }
}
