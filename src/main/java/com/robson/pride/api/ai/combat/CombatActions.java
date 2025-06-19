package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;

import java.util.Map;

public class CombatActions {

    private byte pointer = 0;

    private final Map<Condition, ActionBase> actions;

    public CombatActions(Map<Condition, ActionBase> actions){
        this.actions = actions;
    }

    public void trySelect(PrideMobPatch<?> ent){
        if (ent != null && this.actions != null){
            if (pointer > actions.size()){
                pointer = 0;
            }
            for (Condition condition : actions.keySet()) {
                if (condition.isTrue(ent)){
                    actions.get(condition).tryStart(ent);
                    pointer++;
                    return;
                }
            }
        }
    }
}
