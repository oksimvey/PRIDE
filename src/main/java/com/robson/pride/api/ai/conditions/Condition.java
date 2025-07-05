package com.robson.pride.api.ai.conditions;

import com.robson.pride.api.entity.PrideMobPatch;

public abstract class Condition {

    public abstract boolean isTrue(PrideMobPatch<?> ent);

}
