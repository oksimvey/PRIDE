package com.robson.pride.api.ai.actions.actions;

import com.robson.pride.api.ai.actions.ActionBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

public class MoveTo extends ActionBase {

    @Override
    public void onActionStart(Entity ent, CompoundTag action) {
        if (ent != null && action != null) {
            if (ent instanceof Mob mob) {
                if (action.contains("x") && action.contains("y") && action.contains("z")) {
                    if (action.contains("relative")) {
                        if (action.getBoolean("relative")) {
                            mob.getNavigation().moveTo( mob.getX() + action.getInt("x"), mob.getY() + action.getInt("y"), mob.getZ() + action.getInt("z"), 5);
                        }
                    }
                    mob.getNavigation().moveTo(action.getInt("x"), action.getInt("y"), action.getInt("z"), 5);
                }
            }
        }
    }
}
