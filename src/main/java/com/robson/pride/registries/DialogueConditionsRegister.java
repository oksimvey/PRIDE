package com.robson.pride.registries;

import com.robson.pride.api.ai.dialogues.DialogueConditionBase;
import com.robson.pride.api.ai.dialogues.conditions.*;

import java.util.HashMap;
import java.util.Map;

public class DialogueConditionsRegister {

    public static Map<String, DialogueConditionBase> dialogueConditions = new HashMap<>();

    public static void register() {
        dialogueConditions.put("isRaining", new IsRainingCondition());
        dialogueConditions.put("entityType", new EntityTypeCondition());
        dialogueConditions.put("time", new TimeCondition());
        dialogueConditions.put("chance", new RandomChanceCondition());
        dialogueConditions.put("cooldown", new CooldownCondition());
    }
}
