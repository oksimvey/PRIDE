package com.robson.pride.events;

import com.robson.pride.item.weapons.CustomWeaponModelBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.robson.pride.api.maps.WeaponsMap.WEAPONS;

@Mod.EventBusSubscriber(
        modid = "pride",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = {Dist.CLIENT}
)
public class ClientSetupEvent {

    @SubscribeEvent
    public static void registerSpecialModels(ModelEvent.RegisterAdditional event) {
        for (String weapon : WEAPONS.keySet()) {
           event.register(CustomWeaponModelBase.getWeaponModelLocation(WEAPONS.get(weapon).getModel()));
       }
    }

    @SubscribeEvent
    public static void replaceItemModels(ModelEvent.ModifyBakingResult event) {
        ModelResourceLocation key = new ModelResourceLocation(new ResourceLocation("pride:weapon"), "inventory");
        event.getModels().compute(key, (k, oldModel) -> new CustomWeaponModelBase(oldModel, event.getModelBakery()));
    }
}

