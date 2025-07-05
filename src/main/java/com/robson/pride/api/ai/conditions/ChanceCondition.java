package com.robson.pride.api.ai.conditions;

import com.robson.pride.api.entity.PrideMobPatch;

import java.util.Random;

public class ChanceCondition extends Condition {

    private final byte chance;

    public ChanceCondition(byte chance) {
        this.chance = chance;
    }

    public boolean isTrue(PrideMobPatch<?> ent) {
      return this.chance >= (byte) (new Random().nextInt(100));
    }
}
