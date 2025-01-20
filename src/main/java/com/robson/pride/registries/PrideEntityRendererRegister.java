package com.robson.pride.registries;

import com.robson.pride.entities.forest.hunter.HunterRenderer;
import com.robson.pride.entities.forest.magmamonster.MagmaMonsterRenderer;
import com.robson.pride.entities.pre_hardmode.japanese.boss.shogun.ShogunRenderer;
import com.robson.pride.entities.pre_hardmode.japanese.mob.ronin.RoninRenderer;
import com.robson.pride.entities.pre_hardmode.knight.mob.eliteknight.EliteKnightRenderer;
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
        event.registerEntityRenderer(EntityRegister.RONIN.get(), RoninRenderer::new);
        event.registerEntityRenderer(EntityRegister.SHOGUN.get(), ShogunRenderer::new);
        event.registerEntityRenderer(EntityRegister.ELITE_KNIGHT.get(), EliteKnightRenderer::new);
        event.registerEntityRenderer(EntityRegister.CLONE_ENTITY.get(), CloneEntityRenderer::new);
        event.registerEntityRenderer(EntityRegister.HUNTER.get(), HunterRenderer::new);
        event.registerEntityRenderer(EntityRegister.SHOOTER.get(), ShooterRenderer::new);
        event.registerEntityRenderer(EntityRegister.MAGMA_MONSTER.get(), MagmaMonsterRenderer::new);
    }
}