package com.robson.pride.registries;

import com.robson.pride.api.entity.PrideMobRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.gameasset.Armatures;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PrideEntityRendererRegister {

    @SubscribeEvent
    public static void registerSpecialEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegister.PRIDE_MOB.get(), PrideMobRenderer::new);
    }

    @SubscribeEvent
    public static void onPatchedRenderer(PatchedRenderersEvent.Add event){
        event.addPatchedEntityRenderer(EntityRegister.PRIDE_MOB.get(),(entityType )-> (new PHumanoidRenderer(Meshes.BIPED, event.getContext(), entityType)).initLayerLast(event.getContext(), entityType));
    }

}