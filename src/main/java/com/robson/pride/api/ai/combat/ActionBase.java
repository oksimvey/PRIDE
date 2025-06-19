package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;

import java.util.List;

public abstract class ActionBase {

    private final List<Condition> conditions;

    public ActionBase(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public void tryStart(PrideMobPatch<?> ent){
        if (ent != null && this.conditions != null){
            byte trueConditions = 0;
            for (Condition condition : conditions) {
                if (condition.isTrue(ent)) {
                    trueConditions++;
                }
            }
            if (trueConditions >= conditions.size()) {
                start(ent);
            }
        }
    }


    protected abstract void start(PrideMobPatch<?> ent);

}
