package com.robson.pride.registries;

import com.robson.pride.effect.HypnotizedEffect;
import com.robson.pride.main.Pride;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.robson.pride.effect.WetEffect;

public class EffectRegister {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Pride.MODID);

    public static final RegistryObject<MobEffect> WET = MOB_EFFECTS.register("wet", WetEffect::new);
    public static final RegistryObject<MobEffect> HYPNOTIZED = MOB_EFFECTS.register("hypnotized", HypnotizedEffect::new);

}
