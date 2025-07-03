package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.utils.TimerUtil;

import java.util.concurrent.TimeUnit;

public class CooldownCondition extends Condition {

    boolean waiting;

    private final int cooldown;

    public CooldownCondition(int cooldown) {
        this.cooldown = cooldown;
        waiting = false;
    }

    public boolean isTrue(PrideMobPatch<?> ent) {
       if (!waiting){
           waiting = true;
           TimerUtil.schedule(()-> waiting = false, this.cooldown, TimeUnit.MILLISECONDS);
           return true;
       }
       return false;
    }
}
