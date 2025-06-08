package com.robson.pride.api.ai.dialogues.conditions;

import com.robson.pride.api.ai.dialogues.DialogueConditionBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class TimeCondition extends DialogueConditionBase {

    @Override
    public boolean isTrue(Entity ent, Entity target, CompoundTag predicate) {
        if (predicate.contains("min") && predicate.contains("max")) {
            return ent.level().getDayTime() >= predicate.getInt("min") && ent.level().getDayTime() <= predicate.getInt("max");
        }
        return false;
    }
}
