package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;

public abstract class Condition {

    public abstract boolean isTrue(PrideMobPatch<?> ent);

}
