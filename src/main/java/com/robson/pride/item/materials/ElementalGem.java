package com.robson.pride.item.materials;

import com.robson.pride.api.data.item.ElementalGemData;
import com.robson.pride.api.data.manager.ElementDataManager;

public interface ElementalGem  {

    ElementalGemData DARKNESS = new ElementalGemData("Darkness", ElementDataManager.DARKNESS);

    ElementalGemData LIGHT = new ElementalGemData("Light", ElementDataManager.LIGHT);

    ElementalGemData THUNDER = new ElementalGemData("Thunder", ElementDataManager.THUNDER);

    ElementalGemData SUN = new ElementalGemData("Sun", ElementDataManager.SUN);

    ElementalGemData MOON = new ElementalGemData("Moon", ElementDataManager.MOON);

    ElementalGemData BLOOD = new ElementalGemData("Blood", ElementDataManager.BLOOD);

    ElementalGemData WIND = new ElementalGemData("Wind", ElementDataManager.WIND);

    ElementalGemData NATURE = new ElementalGemData("Nature", ElementDataManager.NATURE);

    ElementalGemData ICE = new ElementalGemData("Ice", ElementDataManager.ICE);

    ElementalGemData WATER = new ElementalGemData("Water", ElementDataManager.WATER);
}
