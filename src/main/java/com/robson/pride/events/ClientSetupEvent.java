package com.robson.pride.events;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.GenericData;
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
        for (short i = 1; true; i++){
            GenericData data = ServerDataManager.INSTANCE.getByID(i);
            if (data == null){
               break;
            }
            event.register(data.getModel());
        }
        for (short i = 1000; true; i++){
            GenericData data = ServerDataManager.INSTANCE.getByID(i);
            if (data == null){
                break;
            }
            event.register(data.getModel());
        }
        for (short i = 2000; true; i++){
            GenericData data = ServerDataManager.INSTANCE.getByID(i);
            if (data == null){
                break;
            }
            event.register(data.getModel());
        }
        for (short i = 3000; true; i++){
            GenericData data = ServerDataManager.INSTANCE.getByID(i);
            if (data == null){
                break;
            }
            event.register(data.getModel());
        }
    }

    @SubscribeEvent
    public static void replaceItemModels(ModelEvent.ModifyBakingResult event) {
        ModelResourceLocation key = new ModelResourceLocation(new ResourceLocation("pride:item"), "inventory");
        event.getModels().compute(key, (k, oldModel) -> new CustomItemModelBase(oldModel, event.getModelBakery()));
    }
}

