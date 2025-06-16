package com.robson.pride.api.maps;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.item.weapons.Kuronami;

import java.util.Map;

public class WeaponsMap {

   public static Map<String, WeaponData> WEAPONS = Map.ofEntries(
           Map.entry("Kuronami", new Kuronami()));

}
