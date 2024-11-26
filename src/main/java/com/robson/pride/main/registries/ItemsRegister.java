package com.robson.pride.main.registries;

import com.robson.pride.main.Pride;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegister {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Pride.MODID);

    public static final RegistryObject<Item> EuropeanLongsword = REGISTRY.register("european_longsword", com.robson.pride.item.weapons.EuropeanLongsword::new);
    public static final RegistryObject<Item> Kamayari = REGISTRY.register("kamayari", com.robson.pride.item.weapons.Kamayari::new);
    public static final RegistryObject<Item> Odachi = REGISTRY.register("odachi", com.robson.pride.item.weapons.Odachi::new);
}
