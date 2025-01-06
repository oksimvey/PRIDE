package com.robson.pride.registries;

import com.robson.pride.main.Pride;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class PrideTabRegister {

    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pride.MODID);

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> EQUIPMENT_TAB = TABS.register("pride_equipment", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Equipment"))
            .icon(() -> new ItemStack(ItemsRegister.EuropeanLongsword.get()))
            .displayItems((enabledFeatures, entries) -> {
                entries.accept(ItemsRegister.EuropeanLongsword.get());
            })
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());

    public static final RegistryObject<CreativeModeTab> MATERIALS_TAB = TABS.register("pride_materials", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Materials"))
            .icon(() -> new ItemStack(ItemsRegister.DARKNESS_GEM.get()))
            .displayItems((enabledFeatures, entries) -> {
                entries.accept(ItemsRegister.DARKNESS_GEM.get());
                entries.accept(ItemsRegister.LIGHT_GEM.get());
                entries.accept(ItemsRegister.THUNDER_GEM.get());
                entries.accept(ItemsRegister.SUN_GEM.get());
                entries.accept(ItemsRegister.MOON_GEM.get());
                entries.accept(ItemsRegister.BLOOD_GEM.get());
                entries.accept(ItemsRegister.WIND_GEM.get());
                entries.accept(ItemsRegister.NATURE_GEM.get());
                entries.accept(ItemsRegister.ICE_GEM.get());
                entries.accept(ItemsRegister.WATER_GEM.get());
            })
            .withTabsBefore(EQUIPMENT_TAB.getKey())
            .build());

    public static final RegistryObject<CreativeModeTab> SCROLLS_TAB = TABS.register("pride_weapon_arts", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Weapon Arts"))
            .icon(() -> new ItemStack(ItemsRegister.WEAPON_ART.get()))
            .displayItems((parameters, output) -> {
                        output.accept(getWeaponArt("Flame Slash"));
                        output.accept(getWeaponArt("Darkness Cut"));
                    })
            .withTabsBefore(MATERIALS_TAB.getKey())
            .build());

    public static final RegistryObject<CreativeModeTab> ENTITIES_TAB = TABS.register("pride_entities", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Entities"))
            .icon(() -> new ItemStack(ItemsRegister.SPAWN_EGG.get()))
            .displayItems((enabledFeatures, entries) -> {
               entries.accept(getSpawnEgg("Ronin"));
                entries.accept(getSpawnEgg("Shogun"));
                entries.accept(getSpawnEgg("Elite Knight"));
            })
            .withTabsBefore(SCROLLS_TAB.getKey())
            .build());

    private static ItemStack getWeaponArt(String weaponart) {
        ItemStack item = new ItemStack(ItemsRegister.WEAPON_ART.get());
        item.getOrCreateTag().putString("weapon_art", weaponart);
        return item;
    }

    private static ItemStack getSpawnEgg(String entity) {
        ItemStack item = new ItemStack(ItemsRegister.SPAWN_EGG.get());
        item.getOrCreateTag().putString("spawn_egg", entity);
        return item;
    }
}