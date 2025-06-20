package com.robson.pride.api.data.manager;


import com.robson.pride.api.data.WeaponData;
import com.robson.pride.item.weapons.EuropeanLongsword;
import com.robson.pride.item.weapons.Kuronami;

public interface WeaponDataManager {

    enum Weapon {
        KURONAMI,
        EUROPEAN_LONGSWORD
    }

    static WeaponData getByID(int id){
        if (id >= 0 && id < Weapon.values().length) {
                return getByWeapon(Weapon.values()[id]);
        }
        return null;
    }

    static WeaponData getByWeapon(Weapon weapon){
        return switch (weapon){

            case KURONAMI -> Kuronami.WEAPON_DATA;

            case EUROPEAN_LONGSWORD -> EuropeanLongsword.WEAPON_DATA;
        };
    }
}
