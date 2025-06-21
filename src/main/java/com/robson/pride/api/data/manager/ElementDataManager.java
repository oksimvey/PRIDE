package com.robson.pride.api.data.manager;

import com.robson.pride.api.elements.*;

public interface ElementDataManager extends DataManager<ElementBase> {

    byte DARKNESS = 1;

    byte LIGHT = 2;

    byte THUNDER = 3;

    byte SUN = 4;

    byte MOON = 5;

    byte BLOOD = 6;

    byte WIND = 7;

    byte NATURE = 8;

    byte ICE = 9;

    byte WATER = 10;

    byte NEUTRAL = 11;

    ElementDataManager INSTANCE = id -> switch (id) {
        case DARKNESS -> DarknessElement.DATA;

        case LIGHT -> LightElement.DATA;

        case THUNDER -> ThunderElement.DATA;

        case SUN -> SunElement.DATA;

        case MOON -> MoonElement.DATA;

        case BLOOD -> BloodElement.DATA;

        case WIND -> WindElement.DATA;

        case NATURE -> NatureElement.DATA;

        case ICE -> IceElement.DATA;

        case WATER -> WaterElement.DATA;

        default -> null;
    };
}
