package com.robson.pride.api.data.types;

import com.robson.pride.api.ai.actions.builder.ActionsBuilder;
import com.robson.pride.api.entity.PrideMob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public abstract class MobData {

    private final ResourceLocation texture;

    private final ActionsBuilder combatActions;

    private final Map<EquipmentSlot, ItemStack> equipment;

    protected MobData(ResourceLocation texture, ActionsBuilder combatActions, Map<EquipmentSlot, ItemStack> equipment) {
        this.texture = texture;
        this.combatActions = combatActions;
        this.equipment = equipment;
    }

    public void equip(PrideMob mob){
        if (mob != null && equipment != null) {
            for (Map.Entry<EquipmentSlot, ItemStack> entry : equipment.entrySet()) {
                mob.setItemSlot(entry.getKey(), entry.getValue());
            }
        }
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public ActionsBuilder getCombatActions() {
        return combatActions;
    }
}
