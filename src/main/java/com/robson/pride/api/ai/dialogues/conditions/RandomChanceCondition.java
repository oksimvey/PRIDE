package com.robson.pride.api.ai.dialogues.conditions;

import com.robson.pride.api.ai.dialogues.DialogueConditionBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

import java.util.Random;

public class RandomChanceCondition extends DialogueConditionBase {

    @Override
    public boolean isTrue(Entity ent, Entity target, CompoundTag predicate) {
        if (predicate.contains("chance")) {
            return new Random().nextInt(101) <= predicate.getInt("chance");
        }
        return false;
    }
}
