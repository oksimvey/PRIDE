package com.robson.pride.registries;

import com.robson.pride.api.skillcore.WeaponArtItem;
import com.robson.pride.item.spawnegg.SpawnEggBase;
import com.robson.pride.item.weapons.*;
import com.robson.pride.main.Pride;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegister {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Pride.MODID);

    public static final RegistryObject<Item> CUSTOM_WEAPON_ITEM = REGISTRY.register("weapon", CustomItem::new);

    public static final RegistryObject<Item> WEAPON_ART = REGISTRY.register("weapon_art",
            () -> new WeaponArtItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SPAWN_EGG = REGISTRY.register("spawn_egg",
            () -> new SpawnEggBase(new Item.Properties().stacksTo(64)));
}
