package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.dialogues.AnswerGUI;
import com.robson.pride.api.entity.PrideMobPatch;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.ConcurrentHashMap;

public class QuestionAction extends DialogueAction {

    public static ConcurrentHashMap<Player, AnswerGUI> isAnswering = new ConcurrentHashMap<>();

    public QuestionAction(String subtitle, int duration, String sound, float volume) {
        super(subtitle, duration, sound, volume);
    }

    @Override
    public void start(PrideMobPatch<?> ent){
        super.start(ent);

    }
}
