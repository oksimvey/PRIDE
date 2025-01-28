package com.robson.pride.registries;

import com.robson.pride.api.npc.DialogueConditionBase;
import com.robson.pride.api.npc.conditions.EntityTypeCondition;
import com.robson.pride.api.npc.conditions.IsRainingCondition;

import java.util.HashMap;
import java.util.Map;

public class DialogueConditionsRegister {

    public static Map<String, DialogueConditionBase> dialogueConditions = new HashMap<>();

    public static void register(){
        dialogueConditions.put("isRaining", new IsRainingCondition());
        dialogueConditions.put("entityType", new EntityTypeCondition());
    }
}
