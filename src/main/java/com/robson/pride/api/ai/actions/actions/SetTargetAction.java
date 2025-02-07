package com.robson.pride.api.ai.actions.actions;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.api.utils.TargetUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class SetTargetAction  extends ActionBase {

    @Override
    public void onActionStart(Entity ent, CompoundTag action) {
        if (ent != null){
            if (ent instanceof PrideMobBase mobBase){
                if (action.contains("range")){
                    for (Entity target : mobBase.level().getEntities(mobBase, MathUtils.createAABBAroundEnt(ent, action.getInt("range")))){
                        if (target != null){
                            if (action.contains("entity_types")){
                                ListTag entitytypes = action.getList("entity_types", 8);
                                for (int i = 0; i < entitytypes.size(); ++i){
                                    if (EntityType.getKey(target.getType()).toString().equals(entitytypes.getString(i))){
                                        TargetUtil.setTarget(mobBase, target);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
