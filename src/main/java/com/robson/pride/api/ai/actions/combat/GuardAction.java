package com.robson.pride.api.ai.actions.combat;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.entity.PrideMobPatch;

public class GuardAction extends ActionBase {

    private final int duration;

    private final boolean canParry;

    public GuardAction(int duration, boolean canParry) {
       this.duration = duration;
       this.canParry = canParry;
    }

    public void start(PrideMobPatch<?> ent) {

    }
}
