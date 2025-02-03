package com.robson.pride.registries;

import com.robson.pride.effect.DarknessWrathEffect;
import com.robson.pride.effect.DivineProtectionEffect;
import com.robson.pride.effect.HypnotizedEffect;
import com.robson.pride.effect.WetEffect;
import com.robson.pride.main.Pride;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectRegister {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Pride.MODID);

    public static final RegistryObject<MobEffect> DIVINE_PROTECTION = MOB_EFFECTS.register("divine_protection", DivineProtectionEffect::new);
    public static final RegistryObject<MobEffect> WET = MOB_EFFECTS.register("wet", WetEffect::new);
    public static final RegistryObject<MobEffect> HYPNOTIZED = MOB_EFFECTS.register("hypnotized", HypnotizedEffect::new);
    public static final RegistryObject<MobEffect> DARKNESS_WRATH = MOB_EFFECTS.register("darkness_wrath", DarknessWrathEffect::new);

}
