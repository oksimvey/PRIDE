package com.robson.pride.api.maps;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.item.weapons.EuropeanLongsword;
import com.robson.pride.item.weapons.Kuronami;

import java.util.Map;

public interface WeaponsMap {

    Map<String, WeaponData> WEAPONS = Map.ofEntries(
        Map.entry("Kuronami", new Kuronami()),
            Map.entry("European Longsword", new EuropeanLongsword())
    );
}
