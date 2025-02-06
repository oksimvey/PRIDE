package com.robson.pride.api.ai.goals;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.registries.ActionsRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;

import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.ai.dialogues.JsonInteractionsReader.deserializeConditions;

public class JsonGoalsReader {

    public static void onEntTick(PrideMobBase prideMobBase) {
        if (prideMobBase != null) {
            CompoundTag tagmap = PrideMobPatchReloader.MOB_TAGS.get(prideMobBase.getType());
            if (tagmap != null) {
                ListTag goals = tagmap.getList("goals", 10);
                if (goals != null) {
                    deserializeGoals(prideMobBase, goals);
                }
            }
        }
    }

    public static void deserializeGoals(PrideMobBase prideMobBase, ListTag goals) {
        if (prideMobBase != null && goals != null) {
            for (int i = 0; i < goals.size(); ++i) {
                CompoundTag goal = goals.getCompound(i);
                if (goal.contains("conditions")) {
                    ListTag conditions = goal.getList("conditions", 10);
                    if (deserializeConditions(prideMobBase, prideMobBase, conditions)) {
                        if (goal.contains("actions")){
                            deserializeActions( prideMobBase, goal.getList("actions", 10), (byte) 0);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void deserializeOnEntityDeathGoals(PrideMobBase prideMobBase){

    }

    public static void deserializeOnEntityAttackedGoals(){}

    public static void deserializeActions(Entity ent, ListTag tag, byte i){
        if (ent != null && tag != null){
            if (i < tag.size()){
                CompoundTag action = tag.getCompound(i);
                if (action.contains("action")){
                    ActionBase actionBase = ActionsRegister.actions.get(action.getString("action"));
                    if (actionBase != null){
                        actionBase.onActionStart(ent, action);
                    }
                    if (action.contains("duration")){
                        TimerUtil.schedule(()-> deserializeActions(ent, tag, (byte) (i + 1)), action.getInt("duration"), TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }
}
