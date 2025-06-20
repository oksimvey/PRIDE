package com.robson.pride.api.data.manager;

import com.robson.pride.api.elements.*;

public interface ElementDataManager {

    enum Element {
        DARKNESS,
        LIGHT,
        THUNDER,
        SUN,
        MOON,
        BLOOD,
        WIND,
        NATURE,
        ICE,
        WATER
    }


    static ElementBase getByID(String id){
        Element element;
        try {
            element = Element.valueOf(id.toUpperCase());
            return getByElement(element);
        }
        catch (IllegalArgumentException d){
            return null;
        }
    }

    static ElementBase getByElement(Element element){
        return switch (element){

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
        };
    }
}
