package com.robson.pride.api.ai.actions.actions;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class MoveToPosAction extends ActionBase {

    @Override
    public void onActionStart(Entity ent, CompoundTag action) {
        if (ent != null && action != null) {
            if (ent instanceof PrideMobBase mob) {
                if (action.contains("path")){

                }
            }
        }
    }
}
