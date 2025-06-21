package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;

import java.util.List;

public class ConditionalAction {

    private final List<Condition> conditions;

    private final ActionBase action;

    public ConditionalAction(List<Condition> conditions, ActionBase action) {
        this.conditions = conditions;
        this.action = action;
    }

    public boolean tryStart(PrideMobPatch<?> ent) {
        if (ent != null && this.conditions != null && this.action != null) {
           byte trueConditions = 0;
           for (Condition condition : conditions) {
               if (condition.isTrue(ent)){
                   trueConditions++;
               }
           }
           if (trueConditions == conditions.size()){
               action.start(ent);
               return true;
           }
        }
        return false;
    }

}
