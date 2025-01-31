package com.robson.pride.api.ai.actions.actions;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.ai.dialogues.JsonInteractionsReader;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.utils.MathUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import static com.robson.pride.api.ai.dialogues.JsonInteractionsReader.isSpeaking;

public class InteractAction extends ActionBase {

    @Override
    public void onActionStart(Entity ent, CompoundTag action) {
        if (ent != null) {
            if (action.contains("interaction_range")) {
                for (Entity target : ent.level().getEntities(ent, MathUtils.createAABBAroundEnt(ent, action.getInt("interaction_range")))) {
                    if (target != null) {
                        if (isSpeaking.get(ent) == null && isSpeaking.get(target) == null) {
                            if (action.contains("entity_types")) {
                                ListTag entitypes = action.getList("entity_types", 8);
                                for (int i = 0; i < entitypes.size(); ++i) {
                                    if (EntityType.getKey(target.getType()).toString().equals(entitypes.getString(i))) {
                                        if (action.contains("name")) {
                                            return;
                                        }
                                        JsonInteractionsReader.onInteraction(target, ent);
                                        if (ent instanceof PrideMobBase prideMobBase) {
                                            prideMobBase.setTargetpos(target.position());
                                        }
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
