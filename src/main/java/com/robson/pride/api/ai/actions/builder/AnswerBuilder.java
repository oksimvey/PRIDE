package com.robson.pride.api.ai.actions.builder;

import com.robson.pride.api.ai.actions.interaction.DialogueWithActionAction;

public class AnswerBuilder {

    private final String buttontext;

    private final DialogueWithActionAction action;

    public AnswerBuilder(String buttontext, DialogueWithActionAction action) {
        this.buttontext = buttontext;
        this.action = action;
    }

    public DialogueWithActionAction getAction() {
        return action;
    }

    public String getButtonText() {
        return buttontext;
    }
}
