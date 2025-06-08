package com.robson.pride.registries;

import com.robson.pride.api.elements.*;

import java.util.HashMap;
import java.util.Map;

public class ElementsRegister {

    public static Map<String, ElementBase> elements = new HashMap<>();

    public static void register() {
        elements.put("Darkness", new DarknessElement());
        elements.put("Light", new LightElement());
        elements.put("Thunder", new ThunderElement());
        elements.put("Sun", new SunElement());
        elements.put("Moon", new MoonElement());
        elements.put("Blood", new BloodElement());
        elements.put("Wind", new WindElement());
        elements.put("Nature", new NatureElement());
        elements.put("Ice", new IceElement());
        elements.put("Water", new WaterElement());
    }
}
