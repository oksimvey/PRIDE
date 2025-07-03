package com.robson.pride.registries;

import com.robson.pride.api.item.CustomItem;
import com.robson.pride.main.Pride;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegister {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Pride.MOD_ID);

    public static final RegistryObject<Item> CUSTOM_WEAPON_ITEM = REGISTRY.register("weapon", CustomItem::new);


}
