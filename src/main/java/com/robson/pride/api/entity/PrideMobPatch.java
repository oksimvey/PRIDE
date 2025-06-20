package com.robson.pride.api.entity;

import com.robson.pride.api.ai.combat.CombatActions;
import com.robson.pride.api.ai.combat.HumanoidCombatActions;
import net.minecraft.world.entity.PathfinderMob;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public abstract class PrideMobPatch <T extends PathfinderMob> extends MobPatch<T> {

    private boolean isHumanoid;

    private CombatActions combatActions;

    private HumanoidCombatActions humanoidCombatActions;

    private CombatActions hurtActions;

    private HumanoidCombatActions hurtHumanoidActions;

    private float stamina;

}
