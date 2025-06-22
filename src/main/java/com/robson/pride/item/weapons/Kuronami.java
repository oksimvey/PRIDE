package com.robson.pride.item.weapons;

import com.robson.pride.api.data.item.WeaponData;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.manager.WeaponSkillsDataManager;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;

public interface Kuronami {

    WeaponData WEAPON_DATA = new WeaponData("Kuronami", "katana", 10, 1f, 2, 3, 2,
            10, "minecraft:item/creationsplitter", ElementDataManager.DARKNESS,
            new Matrix2f(-0.5f, -0.5f, -0.15f, 0.2f, 0.8f, 1.5f),
            LongSwordWeaponSkill.ID,
            new WeaponData.AttributeReqs('B', 'C', 'S', (byte) 10, (byte) 5, (byte) 10),
            new FixedRGB(0, 0, 0));

}

