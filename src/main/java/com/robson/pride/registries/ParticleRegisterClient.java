package com.robson.pride.registries;

import com.robson.pride.particles.PerilousParticle;
import com.robson.pride.particles.VulnerableParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleRegisterClient {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegister.PERILOUS.get(), PerilousParticle::provider);
        event.registerSpriteSet(ParticleRegister.VULNERABLE.get(), VulnerableParticle::provider);
    }
}
