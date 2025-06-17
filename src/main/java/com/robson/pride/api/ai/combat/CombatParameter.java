package com.robson.pride.api.ai.combat;

public class CombatParameter {

    private final boolean conditionToPass;

    private final byte weight;

    private final CombatAction action;

    public CombatParameter(boolean conditionToPass, byte weight, CombatAction action) {
        this.conditionToPass = conditionToPass;
        this.weight = weight;
        this.action = action;
    }
}
