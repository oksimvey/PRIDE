package com.robson.pride.api.ai;

import com.robson.pride.api.ai.conditions.AngleDifference;
import com.robson.pride.api.ai.conditions.IsTargetTargetSelf;
import com.robson.pride.api.ai.conditions.TargetIsTryingPerilousAttack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.data.conditions.Condition;

import java.util.function.Supplier;

public class DataConditions {
    public static final DeferredRegister<Supplier<Condition<?>>> CONDITIONS = DeferredRegister.create(new ResourceLocation("epicfight", "conditions"), "pride");
    private static final RegistryObject<Supplier<Condition<?>>> TargetTargetEqualsSelf;
    private static final RegistryObject<Supplier<Condition<?>>> AngleDiff;
    private static final RegistryObject<Supplier<Condition<?>>> TargetTryingPerilous;

    public DataConditions() {
    }

    static {
        TargetTargetEqualsSelf = CONDITIONS.register((new ResourceLocation("pride", "targetistargetingself")).getPath(), () -> {
            return IsTargetTargetSelf::new;
        });
        AngleDiff = CONDITIONS.register((new ResourceLocation("pride", "angle_difference")).getPath(), () -> {
            return AngleDifference::new;
        });
        TargetTryingPerilous = CONDITIONS.register((new ResourceLocation("pride", "targettryingperilous")).getPath(), () -> {
            return TargetIsTryingPerilousAttack::new;
        });
    }
}
