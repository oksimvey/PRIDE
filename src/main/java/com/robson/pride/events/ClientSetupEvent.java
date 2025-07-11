package com.robson.pride.events;

import com.robson.pride.api.data.manager.DataFileManager;
import com.robson.pride.api.item.CustomItemModelBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "pride",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = {Dist.CLIENT}
)
public class ClientSetupEvent {

    @SubscribeEvent
    public static void registerSpecialModels(ModelEvent.RegisterAdditional event) {
        DataFileManager.registerModels(event);
    }

    @SubscribeEvent
    public static void replaceItemModels(ModelEvent.ModifyBakingResult event) {
        ModelResourceLocation key = new ModelResourceLocation(new ResourceLocation("pride:item"), "inventory");
        event.getModels().compute(key, (k, oldModel) -> new CustomItemModelBase(oldModel, event.getModelBakery()));
    }
}

