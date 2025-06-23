package com.robson.pride.registries;

import com.robson.pride.api.data.types.GenericData;
import com.robson.pride.api.data.manager.DataManager;
import com.robson.pride.item.weapons.CustomItem;
import com.robson.pride.main.Pride;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
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
            .icon(() -> CustomItem.createItem(DataManager.EUROPEAN_LONGSWORD))
            .displayItems((enabledFeatures, entries) -> {
                for (short i = 1000; true; i++){
                    GenericData data = DataManager.getGenericData(i);
                    if (data == null){
                        break;
                    }
                    entries.accept(CustomItem.createItem(i));
                }
                for (short i = 2000; true; i++){
                    GenericData data =  DataManager.getGenericData(i);
                    if (data == null){
                        break;
                    }
                    entries.accept(CustomItem.createItem(i));
                }
                for (short i = 3000; true; i++){
                    GenericData data =  DataManager.getGenericData(i);
                    if (data == null){
                        return;
                    }
                    entries.accept(CustomItem.createItem(i));
                }
            })
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());

    public static final RegistryObject<CreativeModeTab> MATERIALS_TAB = TABS.register("pride_materials", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Materials"))
            .icon(() -> CustomItem.createItem(DataManager.DARKNESS))
            .displayItems((enabledFeatures, entries) -> {
                for (short i = 1; true; i++){
                    GenericData data = DataManager.getGenericData(i);
                    if (data == null){
                        return;
                    }
                    entries.accept(CustomItem.createItem(i));
                }
            })
            .withTabsBefore(EQUIPMENT_TAB.getKey())
            .build());

    public static final RegistryObject<CreativeModeTab> SKILLS_TAB = TABS.register("pride_weapon_arts", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Weapon Arts"))
            .icon(() -> CustomItem.createItem(DataManager.DARKNESS_CUT))
            .displayItems((parameters, output) -> {
                for (short i = 4000; true; i++){
                    GenericData data =  DataManager.getGenericData(i);
                    if (data == null){
                        return;
                    }
                    output.accept(CustomItem.createItem(i));
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

}