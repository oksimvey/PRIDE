package com.robson.pride.registries;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ItemPredicateRegister {

    public static void registerPredicates(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            ItemPropertyFunction ELEMENTAL_GEM = ((itemStack, clientLevel, livingEntity, i) -> {
                String element = itemStack.getOrCreateTag().getString("passive_element");
                switch (element) {
                    case "Darkness" -> {
                        return 0;
                    }
                    case "Light" -> {
                        return 1;
                    }
                    case "Thunder" -> {
                        return 2;
                    }
                    case "Sun" -> {
                        return 3;
                    }
                    case "Moon" -> {
                        return 4;
                    }
                    case "Blood" -> {
                        return 5;
                    }
                    case "Wind" -> {
                        return 6;
                    }
                    case "Nature"->{
                        return 7;
                    }
                    case "Ice" -> {
                        return 8;
                    }
                    case "Water"->{
                        return 9;
                    }
                }
                return 9;
            });

            ItemPropertyFunction PULLING = (((itemStack, clientLevel, livingEntity, i) -> {
                if (livingEntity != null){
               if (AnimationsRegister.pullLvl.get(livingEntity) != null) {
                   if (AnimationsRegister.pullLvl.get(livingEntity) != 0) {
                       return AnimationsRegister.pullLvl.get(livingEntity);
                   }
               }
               }
                return 0;
            }));
            ItemProperties.register(ItemsRegister.MOB_BOW.get(), new ResourceLocation("pride:pulling"), PULLING);
            ItemProperties.register(ItemsRegister.ELEMENTAL_GEM.get(), new ResourceLocation("pride:element"), ELEMENTAL_GEM);
        });
    }
}
