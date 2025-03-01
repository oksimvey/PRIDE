package com.robson.pride.api.entity;

import com.nameless.indestructible.data.AdvancedMobpatchReloader;
import com.nameless.indestructible.world.ai.goal.AdvancedChasingGoal;
import com.nameless.indestructible.world.ai.goal.AdvancedCombatGoal;
import com.nameless.indestructible.world.ai.goal.GuardGoal;
import com.nameless.indestructible.world.ai.task.GuardBehavior;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.robson.pride.api.ai.goals.PrideChasingBehavior;
import com.robson.pride.api.ai.goals.PrideCombatBehavior;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.CrossbowAttack;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.event.entity.living.LivingEvent;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.entity.ai.brain.BrainRecomposer;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

public class PrideMobPatch <T extends PathfinderMob> extends AdvancedCustomHumanoidMobPatch<T> {

    public AdvancedMobpatchReloader.AdvancedCustomHumanoidMobPatchProvider provider;

    public PrideMobPatch(Faction faction, AdvancedMobpatchReloader.AdvancedCustomHumanoidMobPatchProvider provider) {
        super(faction, provider);
        this.provider = provider;
    }


    @Override
    public void serverTick(LivingEvent.LivingTickEvent event){
        super.serverTick(event);
        if (this.getOriginal() instanceof PrideMobBase prideMobBase && prideMobBase.canTickLod(Minecraft.getInstance()))
           prideMobBase.deserializePassiveSkills();
    }

    @Override
    public void setAIAsInfantry(boolean holdingRangedWeapon) {
        boolean isUsingBrain = !((PathfinderMob)this.getOriginal()).getBrain().getRunningBehaviors().isEmpty();
        if (isUsingBrain) {
                CombatBehaviors.Builder<HumanoidMobPatch<?>> builder = this.getHoldingItemWeaponMotionBuilder();
                if (builder != null) {
                    BrainRecomposer.replaceBehavior(((PathfinderMob)this.original).getBrain(), Activity.FIGHT, 0, new PrideCombatBehavior(this, builder.build(this)), new Class[]{MeleeAttack.class});
                    BrainRecomposer.replaceBehavior(((PathfinderMob)this.original).getBrain(), Activity.FIGHT, 0, new GuardBehavior(this, this.provider.getGuardRadius()), new Class[]{CrossbowAttack.class});
                }
                BrainRecomposer.replaceBehavior(((PathfinderMob)this.original).getBrain(), Activity.CORE, 1, new PrideChasingBehavior(this, this.provider.getChasingSpeed(), (double)this.provider.getAttackRadius()), new Class[]{MoveToTargetSink.class});
        }
            CombatBehaviors.Builder<HumanoidMobPatch<?>> builder = this.getHoldingItemWeaponMotionBuilder();
            if (builder != null) {
                ((PathfinderMob)this.original).goalSelector.addGoal(0, new AdvancedCombatGoal(this, builder.build(this)));
                ((PathfinderMob)this.original).goalSelector.addGoal(0, new GuardGoal(this, this.provider.getGuardRadius()));
                ((PathfinderMob)this.original).goalSelector.addGoal(1, new AdvancedChasingGoal(this, (PathfinderMob)this.getOriginal(), this.provider.getChasingSpeed(), true, (double)this.provider.getAttackRadius()));
        }
    }
}
