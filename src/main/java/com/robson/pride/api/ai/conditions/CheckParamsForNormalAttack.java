package com.robson.pride.api.ai.conditions;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class CheckParamsForNormalAttack  extends Condition.EntityPatchCondition {
    @Override
    public CheckParamsForNormalAttack read(CompoundTag tag) {
        return this;
    }

    @Override
    public CompoundTag serializePredicate() {
        return new CompoundTag();
    }

    @Override
    public boolean predicate(LivingEntityPatch<?> target) {
        double veticalDistance = Math.abs(target.getOriginal().getY() - target.getTarget().getY());
        return veticalDistance < target.getOriginal().getEyeHeight();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return List.of();
    }
}
