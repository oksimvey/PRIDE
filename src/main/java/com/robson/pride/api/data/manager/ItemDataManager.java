package com.robson.pride.api.data.manager;


import com.robson.pride.api.data.item.ItemData;
import com.robson.pride.api.data.item.WeaponData;
import com.robson.pride.item.materials.ElementalGem;
import com.robson.pride.item.weapons.EuropeanLongsword;
import com.robson.pride.item.weapons.Kuronami;

public interface ItemDataManager extends DataManager<ItemData> {

    byte DARKNESS_GEM = 0;

    byte LIGHT_GEM = 1;

    byte THUNDER_GEM = 2;

    byte SUN_GEM = 3;

    byte MOON_GEM = 4;

    byte BLOOD_GEM = 5;

    byte WIND_GEM = 6;

    byte NATURE_GEM = 7;

    byte ICE_GEM = 8;

    byte WATER_GEM = 9;

    short KURONAMI = 1000;

    short EUROPEAN_LONGSWORD = 1001;

    short SHIELD = 2000;

    short RING = 3000;

    ItemDataManager INSTANCE = id -> switch (id) {

        case DARKNESS_GEM -> ElementalGem.DARKNESS;

        case LIGHT_GEM -> ElementalGem.LIGHT;

        case THUNDER_GEM -> ElementalGem.THUNDER;

        case SUN_GEM -> ElementalGem.SUN;

        case MOON_GEM -> ElementalGem.MOON;

        case BLOOD_GEM -> ElementalGem.BLOOD;

        case WIND_GEM -> ElementalGem.WIND;

        case NATURE_GEM -> ElementalGem.NATURE;

        case ICE_GEM -> ElementalGem.ICE;

        case WATER_GEM -> ElementalGem.WATER;

        case KURONAMI -> Kuronami.WEAPON_DATA;

        case EUROPEAN_LONGSWORD -> EuropeanLongsword.WEAPON_DATA;

        default -> null;
    };
}
