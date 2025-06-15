package com.robson.pride.api.maps;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import com.robson.pride.item.weapons.Kuronami;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Map;

public interface WeaponsMap {

   Map<String, WeaponData> WEAPONS = Map.ofEntries(
           Map.entry("pride:kuronami", new Kuronami()));

}
