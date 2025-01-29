package com.robson.pride.api.ai.dialogues;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public abstract class DialogueConditionBase {

    public abstract boolean isTrue(Entity ent, Entity target, CompoundTag predicate);

}
