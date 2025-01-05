package com.robson.pride.api.ai.conditions;

import com.robson.pride.api.mechanics.PerilousAttack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class TargetIsTryingPerilousAttack implements Condition<LivingEntityPatch<?>> {
    private boolean invert;

    public TargetIsTryingPerilousAttack(boolean invert) {
        this.invert = invert;
    }

    public TargetIsTryingPerilousAttack() {
    }

    public TargetIsTryingPerilousAttack read(CompoundTag tag) {
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
            boolean TargetIsTryingPerilousAttack = PerilousAttack.checkPerilous(tartgetpatch.getOriginal());
            if (!this.invert) {
                return TargetIsTryingPerilousAttack;
            } else {
                return !TargetIsTryingPerilousAttack;
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}

