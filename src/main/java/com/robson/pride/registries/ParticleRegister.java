package com.robson.pride.registries;

import com.robson.pride.main.Pride;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber
public class ParticleRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Pride.MODID);

    public static final RegistryObject<SimpleParticleType> PERILOUS = PARTICLES.register("perilous", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> VULNERABLE = PARTICLES.register("vulnerable", () -> new SimpleParticleType(true));
}