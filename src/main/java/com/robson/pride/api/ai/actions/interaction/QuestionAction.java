package com.robson.pride.api.ai.actions.interaction;

import com.robson.pride.api.ai.actions.builder.AnswerBuilder;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.gui.AnswerGUI;
import com.robson.pride.api.entity.PrideMobPatch;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionAction extends DialogueAction {

    private final List<AnswerBuilder> answers;

    private final  int delay;

    public QuestionAction(String subtitle, int duration, String sound, float volume, List<AnswerBuilder> answers, int delay) {
        super(subtitle, duration, sound, volume);
        this.answers = answers;
        this.delay = delay;
    }

    @Override
    public void start(PrideMobPatch<?> ent){
        super.start(ent);
        TimerUtil.schedule(()-> {
            Minecraft.getInstance().setScreen(new AnswerGUI(ent, this.answers, getSubtitle()));
        }, this.delay, TimeUnit.MILLISECONDS);
    }
}
