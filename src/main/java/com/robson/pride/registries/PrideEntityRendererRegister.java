package com.robson.pride.registries;

import com.robson.pride.api.entity.PrideMobRenderer;
import com.robson.pride.entities.special.CloneEntityRenderer;
import com.robson.pride.entities.special.ShooterRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PrideEntityRendererRegister {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegister.ELITE_KNIGHT.get(), PrideMobRenderer::new);
        event.registerEntityRenderer(EntityRegister.HUNTER.get(), PrideMobRenderer::new);
        event.registerEntityRenderer(EntityRegister.RONIN.get(), PrideMobRenderer::new);
        event.registerEntityRenderer(EntityRegister.SHOGUN.get(), PrideMobRenderer::new);
        event.registerEntityRenderer(EntityRegister.MAGMA_MONSTER.get(), PrideMobRenderer::new);
        event.registerEntityRenderer(EntityRegister.SHOOTER.get(), ShooterRenderer::new);
        event.registerEntityRenderer(EntityRegister.CLONE_ENTITY.get(), CloneEntityRenderer::new);
    }
}