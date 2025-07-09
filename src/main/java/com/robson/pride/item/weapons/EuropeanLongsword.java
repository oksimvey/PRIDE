package com.robson.pride.item.weapons;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.item.WeaponData;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.network.chat.Component;

public interface EuropeanLongsword {

    WeaponData WEAPON_DATA =
       new WeaponData(Component.literal("European Longsword"), "longsword", 7.5f, 0.75f, 2, 3, 2,
                10, "minecraft:item/longsword", ServerDataManager.NEUTRAL,
                new Matrix2f(-0.5f, -0.5f, -0.15f, 0.2f, 0.8f, 1.5f),
               ServerDataManager.LONGSWORD_PIERCE,
                new WeaponData.AttributeReqs('B', '\0', 'D', (byte) 10, (byte) 0, (byte) 5),
                new FixedRGB((short) 225, (short) 227, (short) 227));

}
