package com.robson.pride.api.ai.goals;

import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.nbt.ListTag;

public class PassiveSkillsReader {

    public static void deserializePassiveSkillsTick(PrideMobBase prideMobBase){
        if (prideMobBase != null){
            if (prideMobBase.skills.contains("path_roll")){
                prideMobBase.deserializePathRoll();
            }
            if (prideMobBase.skills.contains("path_sneak")){
                prideMobBase.deserializePathSneak();
            }
            if (prideMobBase.skills.contains("open_door")){
                prideMobBase.deserializeOpenDoor();
            }
        }
    }

    public static void deserializePassiveSkills(PrideMobBase prideMobBase) {
        if (prideMobBase != null) {
            ListTag targets = PrideMobPatchReloader.SKILLS.get(prideMobBase.getType());
            for (int i = 0; i < targets.size(); ++i){
                prideMobBase.skills.add(targets.getString(i));
            }
        }
    }

}
