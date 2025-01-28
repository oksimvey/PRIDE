package com.robson.pride.api.npc.conditions;

import com.robson.pride.api.npc.DialogueConditionBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class EntityTypeCondition extends DialogueConditionBase {


    @Override
    public boolean isTrue(Entity ent, Entity target, CompoundTag predicate) {
        if (ent != null){
            if (predicate.contains("types")){
                ListTag types = predicate.getList("types", 8);
                for(int i = 0; i < types.getId(); ++i){
                    if (types.getString(i).equals(EntityType.getKey(ent.getType()).toString())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
