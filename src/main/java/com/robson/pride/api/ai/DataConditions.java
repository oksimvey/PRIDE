package com.robson.pride.api.ai;

import com.robson.pride.api.ai.conditions.AngleDifference;
import com.robson.pride.api.ai.conditions.IsNotStuck;
import com.robson.pride.api.ai.conditions.IsTargetTargetSelf;
import com.robson.pride.api.ai.conditions.PassiveSkillDeserialize;
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
    private static final RegistryObject<Supplier<Condition<?>>> IS_NOT_STUCK;

    public DataConditions() {
    }

    static {
        TargetTargetEqualsSelf = CONDITIONS.register((new ResourceLocation("pride", "targetistargetingself")).getPath(), () -> {
            return IsTargetTargetSelf::new;
        });
        AngleDiff = CONDITIONS.register((new ResourceLocation("pride", "angle_difference")).getPath(), () -> {
            return AngleDifference::new;
        });
        TargetTryingPerilous = CONDITIONS.register((new ResourceLocation("pride", "passiveskill")).getPath(), () -> {
            return PassiveSkillDeserialize::new;
        });
        IS_NOT_STUCK = CONDITIONS.register((new ResourceLocation("pride", "not_stuck")).getPath(), ()-> {
            return IsNotStuck::new;
        });
    }
}
