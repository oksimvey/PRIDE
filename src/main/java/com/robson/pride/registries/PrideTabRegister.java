package com.robson.pride.registries;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.manager.WeaponDataManager;
import com.robson.pride.api.data.manager.WeaponSkillsDataManager;
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

import static com.robson.pride.registries.EntityRegister.ENTITIES;
import static com.robson.pride.registries.EntityRegister.SPECIAL_ENTITIES;


public class PrideTabRegister {

    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pride.MODID);

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> EQUIPMENT_TAB = TABS.register("pride_equipment", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Equipment"))
            .icon(() -> CustomWeaponItem.createWeapon(1))
            .displayItems((enabledFeatures, entries) -> {
                for (int i = 0; true; i++){
                    WeaponData data = WeaponDataManager.getByID(i);
                    if (data == null){
                        return;
                    }
                    entries.accept(CustomWeaponItem.createWeapon(i));
                }
            })
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());

    public static final RegistryObject<CreativeModeTab> MATERIALS_TAB = TABS.register("pride_materials", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Materials"))
            .icon(() -> getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), ElementDataManager.DARKNESS))
            .displayItems((enabledFeatures, entries) -> {
                for (byte i = 1; i <= 10; i++) {
                    entries.accept(getElementalGem(ItemsRegister.ELEMENTAL_GEM.get(), i));
                }
            })
            .withTabsBefore(EQUIPMENT_TAB.getKey())
            .build());

    public static final RegistryObject<CreativeModeTab> SKILLS_TAB = TABS.register("pride_weapon_arts", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Weapon Arts"))
            .icon(() -> new ItemStack(ItemsRegister.WEAPON_ART.get()))
            .displayItems((parameters, output) -> {
                for (int i = 1; true; i++){
                    WeaponSkillBase data = WeaponSkillsDataManager.getByID(i);
                    if (data == null){
                        return;
                    }
                    ItemStack item = new ItemStack(ItemsRegister.WEAPON_ART.get());
                    item.getOrCreateTag().putShort("weapon_art", (short) i);
                    item.getOrCreateTag().putString("rarity", data.getSkillRarity());
                    output.accept(CustomWeaponItem.createWeapon(i));
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

    public static ItemStack getElementalGem(Item item, byte element) {
        ItemStack itemStack = new ItemStack(item);
        itemStack.setCount(1);
        itemStack.getOrCreateTag().putByte("passive_element", element);
        return itemStack;
    }
}