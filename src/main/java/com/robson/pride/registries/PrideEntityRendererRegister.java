package com.robson.pride.registries;

import com.robson.pride.client.renderer.japanese.boss.ShogunRenderer;
import com.robson.pride.client.renderer.japanese.mob.RoninRenderer;
import com.robson.pride.client.renderer.knight.mob.EliteKnightRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PrideEntityRendererRegister {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegister.RONIN.get(), RoninRenderer::new);
        event.registerEntityRenderer(EntityRegister.SHOGUN.get(), ShogunRenderer::new);
        event.registerEntityRenderer(EntityRegister.ELITE_KNIGHT.get(), EliteKnightRenderer::new);

    }
}