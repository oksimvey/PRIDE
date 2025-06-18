package com.robson.pride.registries;

import com.robson.pride.main.Pride;
import com.robson.pride.particles.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.client.particle.AnimationTrailParticle;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Pride.MODID);

    public static final RegistryObject<SimpleParticleType> PERILOUS = PARTICLES.register("perilous", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> VULNERABLE = PARTICLES.register("vulnerable", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> RED_LIGHTNING = PARTICLES.register("red_lightning", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> NUMBER_PARTICLE = PARTICLES.register("number_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PRIDE_TRAIL_PARTICLE = PARTICLES.register("pride_trail_particle", () -> new SimpleParticleType(true));

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(PERILOUS.get(), PerilousParticle::provider);
        event.registerSpriteSet(VULNERABLE.get(), VulnerableParticle::provider);
        event.registerSpriteSet(RED_LIGHTNING.get(), RedLightningParticle.Provider::new);
        event.registerSpriteSet(NUMBER_PARTICLE.get(), StringParticle.Provider::new);
        event.registerSpecial(PRIDE_TRAIL_PARTICLE.get(), new AnimationTrailParticle.Provider());
    }
}