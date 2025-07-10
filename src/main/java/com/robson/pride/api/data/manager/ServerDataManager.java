package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.item.WeaponData;
import com.robson.pride.api.data.utils.DynamicMap;
import net.minecraft.world.item.ItemStack;

public class ServerDataManager {

    private static DynamicMap<String, WeaponData> WEAPON_DATA_MAP = new DynamicMap<>((short) 100);


    public static WeaponData getWeaponData(String id){
        if (WEAPON_DATA_MAP.get(id) == null && ServerDataFileManager.getRegistries(ServerDataFileManager.WEAPONS).getList("registries", 8).contains(id)) {
            WEAPON_DATA_MAP.put(id,  new DynamicMap.DynamicMapParameter<>(new WeaponData(ServerDataFileManager.getWeaponData(id))));
        }
        return WEAPON_DATA_MAP.get(id);
    }

    public static WeaponData getWeaponData(ItemStack item){
        if (item != null){
            return getWeaponData(item.getOrCreateTag().getString("pride_id"));
        }
        return null;
    }
}
