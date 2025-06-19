package com.robson.pride.api.maps;

import com.robson.pride.api.elements.*;

import java.util.Map;

public interface ElementsMap {

    Map<String, ElementBase> ELEMENTS = Map.ofEntries(
            Map.entry("Darkness", new DarknessElement()),
            Map.entry("Light", new LightElement()),
            Map.entry("Thunder", new ThunderElement()),
            Map.entry("Sun", new SunElement()),
            Map.entry("Moon", new MoonElement()),
            Map.entry("Blood", new BloodElement()),
            Map.entry("Wind", new WindElement()),
            Map.entry("Nature", new NatureElement()),
            Map.entry("Ice", new IceElement()),
            Map.entry("Water", new WaterElement())
    );
}
