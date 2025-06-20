package com.robson.pride.registries;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class ItemPredicateRegister {

    public static void registerPredicates(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemPropertyFunction ELEMENTAL_GEM = ((itemStack, clientLevel, livingEntity, i) -> itemStack.getOrCreateTag().getByte("passive_element") - 1);
            ItemProperties.register(ItemsRegister.ELEMENTAL_GEM.get(), new ResourceLocation("pride:element"), ELEMENTAL_GEM);
        });
    }
}
