package com.robson.pride.api.ai.dialogues.conditions;

import com.robson.pride.api.ai.dialogues.DialogueConditionBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class CooldownCondition extends DialogueConditionBase {

    @Override
    public boolean isTrue(Entity ent, Entity target, CompoundTag predicate) {
        if (predicate.contains("cooldown")) {
            return ent.tickCount % predicate.getInt("cooldown") == 0;
        }
        return false;
    }
}
