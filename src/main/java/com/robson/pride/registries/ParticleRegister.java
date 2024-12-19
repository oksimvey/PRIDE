package com.robson.pride.registries;

import com.robson.pride.main.Pride;
import com.robson.pride.particles.PerilousParticle;
import com.robson.pride.particles.RedLightningParticle;
import com.robson.pride.particles.VulnerableParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Pride.MODID);

    public static final RegistryObject<SimpleParticleType> PERILOUS = PARTICLES.register("perilous", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> VULNERABLE = PARTICLES.register("vulnerable", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> RED_LIGHTNING = PARTICLES.register("red_lightning", ()->new SimpleParticleType(true));

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(PERILOUS.get(), PerilousParticle::provider);
        event.registerSpriteSet(VULNERABLE.get(), VulnerableParticle::provider);
        event.registerSpriteSet(RED_LIGHTNING.get(), RedLightningParticle.Provider::new);
    }
}