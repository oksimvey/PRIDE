package com.robson.pride.registries;

import com.robson.pride.api.entity.PrideMobRenderer;
import com.robson.pride.entities.special.CloneEntityRenderer;
import com.robson.pride.entities.special.CollidingEntityRenderer;
import com.robson.pride.entities.special.ShooterRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PrideEntityRendererRegister {

    @SubscribeEvent
    public static void registerSpecialEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegister.SHOOTER.get(), ShooterRenderer::new);
        event.registerEntityRenderer(EntityRegister.CLONE_ENTITY.get(), CloneEntityRenderer::new);
        event.registerEntityRenderer(EntityRegister.COLLIDING_ENTITY.get(), CollidingEntityRenderer::new);
        for (RegistryObject<EntityType<?>> entries : EntityRegister.ENTITIES.getEntries()) {
            EntityType type = entries.get();
            event.registerEntityRenderer(type, PrideMobRenderer::new);
        }
    }
}