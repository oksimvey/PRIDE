package com.robson.pride.api.ai.conditions;

import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class IsNotStuck extends Condition.EntityPatchCondition {

    private boolean invert;

    @Override
    public IsNotStuck read(CompoundTag tag) {
        this.invert = tag.contains("invert") && tag.getBoolean("invert");
        return this;
    }

    @Override
    public CompoundTag serializePredicate() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("invert", this.invert);
        return tag;
    }

    @Override
    public boolean predicate(LivingEntityPatch<?> ent) {
       if (ent.getOriginal() instanceof PrideMobBase prideMobBase){
           return this.invert == prideMobBase.shouldPathSneak();
       }
       return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return List.of();
    }
}
