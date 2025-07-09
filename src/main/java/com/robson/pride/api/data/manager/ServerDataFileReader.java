package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.item.WeaponData;
import com.robson.pride.item.weapons.EuropeanLongsword;
import net.minecraft.nbt.CompoundTag;

public class ServerDataFileReader {

    public static WeaponData streamID(Short id){
        return EuropeanLongsword.WEAPON_DATA;
    }

    public static WeaponData readWeaponData(CompoundTag tag){
        return null;
    }
}
