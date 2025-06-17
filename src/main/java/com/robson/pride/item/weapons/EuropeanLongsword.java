package com.robson.pride.item.weapons;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;

public class EuropeanLongsword extends WeaponData {


    public EuropeanLongsword() {
        super("longsword", 7.5f, 0.75f, 2, 3, 2,
                10,"minecraft:item/longsword", "",
                WeaponData.createCollider(0.2f, 0.8f, 1.5f, -0.5f, -0.5f, -0.15f),
                WeaponData.createSkill(new LongSwordWeaponSkill()),
                new AttributeReqs('B', '\0', 'D', (byte) 10, (byte) 0, (byte) 5),
                new TrailParams(new Matrix2f(0,0, -0.2f, 0, 0.2f, -1.75f),
                        255, 255, 255, 10, ""));
    }
}
