package com.robson.pride.api.ai.actions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public abstract class ActionBase {

    public abstract void onActionStart(Entity ent, CompoundTag action);

}
