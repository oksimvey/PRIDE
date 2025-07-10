package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.item.WeaponData;
import com.robson.pride.api.data.utils.DynamicMap;
import com.robson.pride.api.utils.math.MathUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ServerDataManager {

    private static final DynamicMap<String, WeaponData> WEAPON_DATA_MAP = new DynamicMap<>();

    private static long getExpireTime(CompoundTag nbt){
        return (long) 100 + nbt.sizeInBytes();
    }

    public static WeaponData getWeaponData(String id){
        if (WEAPON_DATA_MAP.get(id) == null && ServerDataFileManager.validItem(ServerDataFileManager.WEAPONS, id)) {
            CompoundTag weaponnbt = ServerDataFileManager.getWeaponData(id);
            WEAPON_DATA_MAP.put(id, new WeaponData(weaponnbt), getExpireTime(weaponnbt));
        }
        return WEAPON_DATA_MAP.get(id);
    }

    public static WeaponData getWeaponData(ItemStack item){
        if (item != null){
            return getWeaponData(item.getOrCreateTag().getString("data_id"));
        }
        return null;
    }
}
