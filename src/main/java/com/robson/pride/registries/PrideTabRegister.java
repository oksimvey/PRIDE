package com.robson.pride.registries;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.item.weapons.CustomWeaponItem;
import com.robson.pride.main.Pride;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.robson.pride.api.maps.SkillMap.WEAPON_SKILLS;
import static com.robson.pride.api.maps.WeaponsMap.WEAPONS;
import static com.robson.pride.registries.EntityRegister.ENTITIES;
import static com.robson.pride.registries.EntityRegister.SPECIAL_ENTITIES;
import static com.robson.pride.registries.WeaponSkillRegister.*;


public class PrideTabRegister {

    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pride.MODID);

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> EQUIPMENT_TAB = TABS.register("pride_equipment", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Equipment"))
            .icon(() -> new ItemStack(ItemsRegister.EuropeanLongsword.get()))
            .displayItems((enabledFeatures, entries) -> {
                for (String weaponid : WEAPONS.keySet()){
                    entries.accept(CustomWeaponItem.createWeapon(weaponid));
                }
            })
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());

    public static final RegistryObject<CreativeModeTab> MATERIALS_TAB = TABS.register("pride_materials", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Materials"))
            .icon(() -> getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Darkness"))
            .displayItems((enabledFeatures, entries) -> {
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Darkness"));
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Light"));
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Thunder"));
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Sun"));
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Moon"));
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Blood"));
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Wind"));
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Nature"));
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Ice"));
                entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), "Water"));
            })
            .withTabsBefore(EQUIPMENT_TAB.getKey())
            .build());

    public static final RegistryObject<CreativeModeTab> SKILLS_TAB = TABS.register("pride_weapon_arts", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Weapon Arts"))
            .icon(() -> new ItemStack(ItemsRegister.WEAPON_ART.get()))
            .displayItems((parameters, output) -> {
                List<Map.Entry<String, WeaponSkillBase>> sortedEntries = WEAPON_SKILLS.entrySet()
                        .stream()
                        .sorted(Comparator
                                .comparing((Map.Entry<String, WeaponSkillBase> entry) -> elements.indexOf(entry.getValue().getSkillElement()))
                                .thenComparing(entry -> rarities.indexOf(entry.getValue().getSkillRarity())))
                        .toList();
                for (Map.Entry<String, WeaponSkillBase> entry : sortedEntries) {
                    ItemStack item = new ItemStack(ItemsRegister.WEAPON_ART.get());
                    item.getOrCreateTag().putString("weapon_art", entry.getKey());
                    item.getOrCreateTag().putString("rarity", WEAPON_SKILLS.get(entry.getKey()).getSkillRarity());
                    output.accept(item);
                }
            })
            .withTabsBefore(MATERIALS_TAB.getKey())
            .build());

    public static final RegistryObject<CreativeModeTab> ENTITIES_TAB = TABS.register("pride_entities", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Entities"))
            .icon(() -> new ItemStack(ItemsRegister.SPAWN_EGG.get()))
            .displayItems((enabledFeatures, entries) -> {
                ENTITIES.getEntries().forEach(entry -> {
                    ItemStack item = new ItemStack(ItemsRegister.SPAWN_EGG.get());
                    assert entry.getKey() != null;
                    String entity = entry.getId().toString();
                    item.getOrCreateTag().putString("spawn_egg", entity);
                    entries.accept(item);
                });
                SPECIAL_ENTITIES.getEntries().forEach(entry -> {
                    ItemStack item = new ItemStack(ItemsRegister.SPAWN_EGG.get());
                    assert entry.getKey() != null;
                    String entity = entry.getId().toString();
                    item.getOrCreateTag().putString("spawn_egg", entity);
                    entries.accept(item);
                });

            })
            .withTabsBefore(SKILLS_TAB.getKey())
            .build());

    public static ItemStack getElementalGem(Item item, String element) {
        ItemStack itemStack = new ItemStack(item);
        itemStack.setCount(1);
        itemStack.getOrCreateTag().putString("passive_element", element);
        return itemStack;
    }
}