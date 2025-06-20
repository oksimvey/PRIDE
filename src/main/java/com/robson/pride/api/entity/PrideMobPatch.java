package com.robson.pride.api.entity;

import com.robson.pride.api.ai.combat.CombatActions;
import com.robson.pride.api.ai.combat.HumanoidCombatActions;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.world.entity.PathfinderMob;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.concurrent.TimeUnit;

public abstract class PrideMobPatch <T extends PathfinderMob> extends MobPatch<T> {

    private boolean isHumanoid;

    private CombatActions combatActions;

    private HumanoidCombatActions humanoidCombatActions;

    private CombatActions hurtActions;

    private HumanoidCombatActions hurtHumanoidActions;

    private float stamina;

    private boolean isBlocking;

    private boolean canParry;

    public void startBlocking(int duration, boolean canParry){
        this.isBlocking = true;
        this.canParry = canParry;
        TimerUtil.schedule(()-> {
            this.isBlocking = false;
            this.canParry = false;
        }, duration, TimeUnit.MILLISECONDS);
    }

}
