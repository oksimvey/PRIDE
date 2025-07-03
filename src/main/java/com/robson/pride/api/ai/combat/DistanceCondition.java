package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;

public class DistanceCondition extends Condition {

    private final byte min;

    private final byte max;

    public DistanceCondition(byte min, byte max) {
        this.min = min;
        this.max = max;
    }

    public boolean isTrue(PrideMobPatch<?> ent) {
        if (ent.getTarget() != null) {
            float distance = ent.getOriginal().distanceTo(ent.getTarget());
            return distance >= this.min && distance <= this.max;
        }
        return false;
    }
}
