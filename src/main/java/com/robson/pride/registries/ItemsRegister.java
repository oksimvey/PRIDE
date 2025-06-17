package com.robson.pride.registries;

import com.robson.pride.api.skillcore.WeaponArtItem;
import com.robson.pride.item.materials.Bullet;
import com.robson.pride.item.materials.ElementalGem;
import com.robson.pride.item.spawnegg.SpawnEggBase;
import com.robson.pride.item.weapons.*;
import com.robson.pride.main.Pride;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegister {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Pride.MODID);

    public static final RegistryObject<Item> CUSTOM_WEAPON_ITEM = REGISTRY.register("weapon", CustomWeaponItem::new);
    public static final RegistryObject<Item> BULLET = REGISTRY.register("bullet",
            () -> new Bullet(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> ELEMENTAL_GEM = REGISTRY.register("elemental_gem",
            () -> new ElementalGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WEAPON_ART = REGISTRY.register("weapon_art",
            () -> new WeaponArtItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SPAWN_EGG = REGISTRY.register("spawn_egg",
            () -> new SpawnEggBase(new Item.Properties().stacksTo(64)));
}
