package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.elements.*;

import java.util.List;

public interface ElementDataManager {

    String DARKNESS = "Darkness";

    String LIGHT = "Light";

    String THUNDER = "Thunder";

    String SUN = "Sun";

    String MOON = "Moon";

    String BLOOD = "Blood";

    String WIND = "Wind";

    String NATURE = "Nature";

    String ICE = "Ice";

    String WATER = "Water";

    List<String> VALID_ELEMENTS = List.of(DARKNESS, LIGHT, THUNDER, SUN, MOON, BLOOD, WIND, NATURE, ICE, WATER);

    DataManager<ElementData> MANAGER = new DataManager<>() {

        @Override
        protected ElementData getDefault(String value) {

            return switch (value) {

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
    };
}
