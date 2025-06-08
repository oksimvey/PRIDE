package com.robson.pride.api.ai.conditions;

import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class PassiveSkillDeserialize implements Condition<LivingEntityPatch<?>> {

    public PassiveSkillDeserialize() {
    }

    public PassiveSkillDeserialize read(CompoundTag tag) {
        return this;
    }

    public CompoundTag serializePredicate() {
        return new CompoundTag();
    }

    public boolean predicate(LivingEntityPatch<?> target) {
        return target.getOriginal() instanceof PrideMobBase;
    }

    @OnlyIn(Dist.CLIENT)
    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}

