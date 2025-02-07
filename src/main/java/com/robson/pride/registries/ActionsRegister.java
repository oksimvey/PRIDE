package com.robson.pride.registries;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.ai.actions.actions.*;

import java.util.HashMap;
import java.util.Map;

public class ActionsRegister {

    public static Map<String, ActionBase> actions = new HashMap<>();

    public static void register(){
        actions.put("animation", new PlayAnimationAction());
        actions.put("moveToVec", new MoveToPosAction());
        actions.put("command", new PerformCommand());
        actions.put("interact", new InteractAction());
        actions.put("find_entity", new FindEntityAction());
        actions.put("set_target", new SetTargetAction());
    }
}
