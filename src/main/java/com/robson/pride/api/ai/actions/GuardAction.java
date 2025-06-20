package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.combat.ActionBase;
import com.robson.pride.api.entity.PrideMobPatch;

public class GuardAction extends ActionBase {

    private final int duration;

    private final boolean canParry;

    public GuardAction(int duration, boolean canParry) {
       this.duration = duration;
       this.canParry = canParry;
    }

    protected void start(PrideMobPatch<?> ent) {
       ent.startBlocking(duration, canParry);
    }
}
