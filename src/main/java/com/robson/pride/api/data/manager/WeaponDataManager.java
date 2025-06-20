package com.robson.pride.api.data.manager;


import com.robson.pride.api.data.WeaponData;
import com.robson.pride.item.weapons.EuropeanLongsword;
import com.robson.pride.item.weapons.Kuronami;

public interface WeaponDataManager {

    byte KURONAMI = 0;

    byte EUROPEAN_LONGSWORD = 1;

    static WeaponData getByID(int id){
        return switch (id){

            case KURONAMI -> Kuronami.WEAPON_DATA;

            case EUROPEAN_LONGSWORD -> EuropeanLongsword.WEAPON_DATA;

            default -> null;
        };
    }
}
