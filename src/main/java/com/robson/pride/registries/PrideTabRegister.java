package com.robson.pride.registries;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.GenericData;
import com.robson.pride.api.item.CustomItem;
import com.robson.pride.main.Pride;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class PrideTabRegister {

    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pride.MOD_ID);

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> EQUIPMENT_TAB = TABS.register("pride_equipment", () -> CreativeModeTab.builder()
            .title(Component.literal("Pride Equipment"))
            .icon(() -> CustomItem.createItem(ServerDataManager.EUROPEAN_LONGSWORD))
            .displayItems((enabledFeatures, entries) -> {
                for (short i = 1000; true; i++){
                    GenericData data = ServerDataManager.getGenericData(i);
                    if (data == null){
                        break;
                    }
                    entries.accept(CustomItem.createItem(i));
                }
                for (short i = 2000; true; i++){
                    GenericData data =  ServerDataManager.getGenericData(i);
                    if (data == null){
                        break;
                    }
                    entries.accept(CustomItem.createItem(i));
                }
                for (short i = 3000; true; i++){
                    GenericData data =  ServerDataManager.getGenericData(i);
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
            .icon(() -> CustomItem.createItem(ServerDataManager.DARKNESS))
            .displayItems((enabledFeatures, entries) -> {
                for (short i = 1; true; i++){
                    GenericData data = ServerDataManager.getGenericData(i);
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
            .icon(() -> CustomItem.createItem(ServerDataManager.DARKNESS_CUT))
            .displayItems((parameters, output) -> {
                for (short i = 4000; true; i++){
                    GenericData data =  ServerDataManager.getGenericData(i);
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
            .icon(() -> CustomItem.createItem(ServerDataManager.RONIN))
            .displayItems((enabledFeatures, entries) -> {
                for (short i = 5000; true; i++){
                    GenericData data =  ServerDataManager.getGenericData(i);
                    if (data == null){
                        return;
                    }
                    entries.accept(CustomItem.createItem(i));
                }
            })
            .withTabsBefore(SKILLS_TAB.getKey())
            .build());

}