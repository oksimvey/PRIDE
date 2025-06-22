package com.robson.pride.events;

import com.robson.pride.api.data.item.ItemData;
import com.robson.pride.api.data.manager.ItemDataManager;
import com.robson.pride.api.data.manager.WeaponSkillsDataManager;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.item.weapons.CustomItemModelBase;
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
        for (int i = 0; true; i++){
            ItemData data = ItemDataManager.INSTANCE.getByID(i);
            if (data == null){
               break;
            }
            event.register(data.getModel());
        }
        for (int i = 1000; true; i++){
            ItemData data = ItemDataManager.INSTANCE.getByID(i);
            if (data == null){
                break;
            }
            event.register(data.getModel());
        }
        for (int i = 2000; true; i++){
            ItemData data = ItemDataManager.INSTANCE.getByID(i);
            if (data == null){
                break;
            }
            event.register(data.getModel());
        }
        for (int i = 3000; true; i++){
            ItemData data = ItemDataManager.INSTANCE.getByID(i);
            if (data == null){
                break;
            }
            event.register(data.getModel());
        }
    }

    @SubscribeEvent
    public static void replaceItemModels(ModelEvent.ModifyBakingResult event) {
        ModelResourceLocation key = new ModelResourceLocation(new ResourceLocation("pride:weapon"), "inventory");
        event.getModels().compute(key, (k, oldModel) -> new CustomItemModelBase(oldModel, event.getModelBakery()));
    }
}

