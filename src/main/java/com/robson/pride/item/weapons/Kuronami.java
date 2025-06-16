package com.robson.pride.item.weapons;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.world.phys.Vec3;

public class Kuronami extends WeaponData {


    public Kuronami() {
        super("katana", 10, 1f, 2, 3, 2,
                10,"minecraft:item/creationsplitter", "Darkness",
                WeaponData.createCollider(0.2f, 0.8f, 1.5f, -0.5f, -0.5f, -0.15f),
                WeaponData.createSkill(new LongSwordWeaponSkill()),
                new AttributeReqs('B', 'C', 'S', (byte) 10, (byte) 5, (byte) 10),
                new TrailParams(new Matrix2f(0,0, -0.2f, 0, 0.2f, -1.75f),
                        255, 255, 255, 20, ""));
    }
}
