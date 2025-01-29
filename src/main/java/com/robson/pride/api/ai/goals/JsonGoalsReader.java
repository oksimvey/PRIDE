package com.robson.pride.api.ai.goals;

import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;

public class JsonGoalsReader {

    public static void onEntTick(PrideMobBase prideMobBase) {
        if (prideMobBase != null) {
            ListTag goals = PrideMobPatchReloader.GOALS.get(prideMobBase.getType());
            if (goals != null){
                deserializeGoals(prideMobBase, goals);
            }
        }
    }

    public static void deserializeTargetGoals(PrideMobBase prideMobBase) {
        if (prideMobBase != null) {
            ListTag targets = PrideMobPatchReloader.TARGETS.get(prideMobBase.getType());
            for (int i = 0; i < targets.size(); ++i){
                prideMobBase.targets.add(targets.getString(i));
            }
        }
    }

    public static void deserializeGoals(PrideMobBase prideMobBase, ListTag goals){
        if (prideMobBase != null && goals != null){

        }
    }

    public static void deserializeOnEntityDeathGoals(PrideMobBase prideMobBase){

    }

    public static void deserializeOnEntityAttackedGoals(){}

    public static void deserializeActions(Entity ent, Entity sourceent, ListTag tag){
        if (ent != null){
        }
    }
}
