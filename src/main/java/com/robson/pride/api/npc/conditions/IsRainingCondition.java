package com.robson.pride.api.npc.conditions;

import com.robson.pride.api.npc.DialogueConditionBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class IsRainingCondition extends DialogueConditionBase {

    @Override
    public boolean isTrue(Entity ent, Entity target, CompoundTag predicate) {
        if (predicate.contains("invert")){
            if(predicate.getBoolean("invert")){
                return !target.level().isRaining();
            }
        }
        return target.level().isRaining();
    }
}