package com.robson.pride.registries;

import com.robson.pride.item.materials.gems.*;
import com.robson.pride.item.weaponarts.FlameSlashWeaponArt;
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
            () -> new FlameSlashWeaponArt(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DARKNESS_GEM = REGISTRY.register("darkness_gem",
            () -> new DarknessGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LIGHT_GEM = REGISTRY.register("light_gem",
            () -> new LightGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> THUNDER_GEM = REGISTRY.register("thunder_gem",
            () -> new ThunderGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SUN_GEM = REGISTRY.register("sun_gem",
            () -> new SunGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MOON_GEM = REGISTRY.register("moon_gem",
            () -> new MoonGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BLOOD_GEM = REGISTRY.register("blood_gem",
            () -> new BloodGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WIND_GEM = REGISTRY.register("wind_gem",
            () -> new WindGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NATURE_GEM = REGISTRY.register("nature_gem",
            () -> new NatureGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ICE_GEM = REGISTRY.register("ice_gem",
            () -> new IceGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WATER_GEM = REGISTRY.register("water_gem",
            () -> new WaterGem(new Item.Properties().stacksTo(1)));
}
