package com.robson.pride.api.npc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class DialogueConditions {

    public static boolean deserealizeConditions(Entity target, Entity ent, CompoundTag tag){
        if (target != null && ent != null && tag != null){
            if(tag.contains("predicate")) {
                switch (tag.getString("predicate")) {
                    case "isRaining"-> {
                        if (tag.getBoolean("invert")) {
                            return !isRaining(ent);
                        }
                        return isRaining(ent);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isRaining(Entity ent){
        return ent.level().isRaining();
    }
}
