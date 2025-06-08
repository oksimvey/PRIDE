package com.robson.pride.api.ai.actions.actions;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.utils.AnimUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class PlayAnimationAction extends ActionBase {

    @Override
    public void onActionStart(Entity ent, CompoundTag action) {
        if (action.contains("animation")) {
            float converttime = 0;
            if (action.contains("convert")) {
                converttime = (float) action.getDouble("convert");
            }
            AnimUtils.playAnimByString(ent, action.getString("animation"), converttime);
        }
    }
}
