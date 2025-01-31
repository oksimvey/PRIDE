package com.robson.pride.api.ai.actions.actions;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.utils.CommandUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class PerformCommand extends ActionBase {
    @Override
    public void onActionStart(Entity ent, CompoundTag action) {
        if (action.contains("command")){
            CommandUtils.executeonEntity(ent, action.getString("command"));
        }
    }
}
