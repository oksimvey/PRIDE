package com.robson.pride.item.weapons;

import com.robson.pride.api.data.types.WeaponData;
import com.robson.pride.api.data.manager.DataManager;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.network.chat.Component;

public interface EuropeanLongsword {

    WeaponData WEAPON_DATA =
       new WeaponData(Component.literal("European Longsword"), "longsword", 7.5f, 0.75f, 2, 3, 2,
                10, "minecraft:item/longsword", DataManager.NEUTRAL,
                new Matrix2f(-0.5f, -0.5f, -0.15f, 0.2f, 0.8f, 1.5f),
               DataManager.LONGSWORD_PIERCE,
                new WeaponData.AttributeReqs('B', '\0', 'D', (byte) 10, (byte) 0, (byte) 5),
                new FixedRGB(225, 227, 227));

}
