package com.robson.pride.item.weapons;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.manager.WeaponSkillsDataManager;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;

public interface EuropeanLongsword {

    WeaponData WEAPON_DATA =
       new WeaponData("European Longsword", "longsword", 7.5f, 0.75f, 2, 3, 2,
                10, "minecraft:item/longsword", ElementDataManager.NEUTRAL,
                WeaponData.createCollider(0.2f, 0.8f, 1.5f, -0.5f, -0.5f, -0.15f),
                WeaponSkillsDataManager.LONGSWORD_PIERCE,
                new WeaponData.AttributeReqs('B', '\0', 'D', (byte) 10, (byte) 0, (byte) 5),
                new WeaponData.TrailParams(new Matrix2f(0, 0, -0.2f, 0, 0.2f, -1.75f),
                        255, 255, 255, 10));

}
