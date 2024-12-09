package com.robson.pride.registries;

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
    public static final RegistryObject<Item> FLAME_SLASH_WEAPON_ART = REGISTRY.register("flame_slash_weapon_art",
            () -> new com.robson.pride.item.weaponarts.FlameSlashWeaponArt(new Item.Properties().stacksTo(1)));
}
