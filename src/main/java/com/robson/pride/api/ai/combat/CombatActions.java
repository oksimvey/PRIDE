package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;

import java.util.List;
import java.util.Map;

public class CombatActions {

    private byte pointer = 0;

    private final Map<List<Condition>, ActionBase> actions;

    public CombatActions(Map<List<Condition>, ActionBase> actions){
        this.actions = actions;
    }

    public void trySelect(PrideMobPatch<?> ent){
        if (ent != null && this.actions != null){
            if (pointer > actions.size()){
                pointer = 0;
            }
            for (List<Condition> conditions : actions.keySet()) {
                byte trueConditions = 0;
                for (Condition condition : conditions) {
                    if (condition.isTrue(ent)){
                        trueConditions++;
                    }
                }
                if (trueConditions == conditions.size()){
                    actions.get(conditions).start(ent);
                    pointer++;
                    return;
                }
            }
        }
    }
}
