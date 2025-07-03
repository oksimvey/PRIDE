package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;

import java.util.List;

public class ActionsBuilder {

    private byte pointer = 0;

    private final List<ConditionalAction> actions;

    public ActionsBuilder(List<ConditionalAction> actions){
        this.actions = actions;
    }

    public void trySelect(PrideMobPatch<?> ent){
        if (ent != null && this.actions != null){
            if (pointer >= actions.size()){
                pointer = 0;
            }
            boolean result = actions.get(pointer).tryStart(ent);
            if (!result){
                pointer++;
            }
        }
    }
}
