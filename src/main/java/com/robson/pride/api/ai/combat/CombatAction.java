package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;

public abstract class CombatAction {

    public abstract void start(PrideMobPatch<?> ent);

    public abstract boolean canPerform(PrideMobPatch<?> ent);
}
