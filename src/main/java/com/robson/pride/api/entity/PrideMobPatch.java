package com.robson.pride.api.entity;

import com.nameless.indestructible.data.AdvancedMobpatchReloader;
import com.nameless.indestructible.world.ai.goal.AdvancedChasingGoal;
import com.nameless.indestructible.world.ai.goal.AdvancedCombatGoal;
import com.nameless.indestructible.world.ai.goal.GuardGoal;
import com.nameless.indestructible.world.ai.task.GuardBehavior;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.nameless.indestructible.world.capability.AdvancedCustomMobPatch;
import com.robson.pride.api.ai.goals.PrideChasingBehavior;
import com.robson.pride.api.ai.goals.PrideCombatBehavior;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.CrossbowAttack;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.schedule.Activity;
import yesman.epicfight.api.model.Armature;
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

}
