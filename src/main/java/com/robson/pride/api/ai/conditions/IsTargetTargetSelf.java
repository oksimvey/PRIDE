package com.robson.pride.api.ai.conditions;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class IsTargetTargetSelf implements Condition<LivingEntityPatch<?>> {
    private boolean invert;

    public IsTargetTargetSelf(boolean invert) {
        this.invert = invert;
    }

    public IsTargetTargetSelf() {
    }

    public IsTargetTargetSelf read(CompoundTag tag) {
        this.invert = tag.contains("invert") && tag.getBoolean("invert");
        return this;
    }

    public CompoundTag serializePredicate() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("invert", this.invert);
        return tag;
    }

    public boolean predicate(LivingEntityPatch<?> target) {
        LivingEntityPatch<?> tartgetpatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(target.getTarget(), LivingEntityPatch.class);
        if (tartgetpatch != null) {
            boolean IsTargetTargetSelf = tartgetpatch.getTarget() == target.getOriginal();
            if (!this.invert) {
                return IsTargetTargetSelf;
            } else {
                return !IsTargetTargetSelf;
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public List<Condition.ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}
