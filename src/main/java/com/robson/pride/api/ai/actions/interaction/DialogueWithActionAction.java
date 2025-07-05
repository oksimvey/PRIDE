package com.robson.pride.api.ai.actions.interaction;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.utils.TimerUtil;

import java.util.concurrent.TimeUnit;

public class DialogueWithActionAction extends DialogueAction {

    private final ActionBase action;

    private final int delay;

    public DialogueWithActionAction(String subtitle, int duration, String sound, float volume, ActionBase action, int delay) {
        super(subtitle, duration, sound, volume);
        this.action = action;
        this.delay = delay;
    }

    public void start(PrideMobPatch<?> ent) {
        super.start(ent);
        TimerUtil.schedule(()-> {
            action.start(ent);
        }, delay, TimeUnit.MILLISECONDS);
    }
}
