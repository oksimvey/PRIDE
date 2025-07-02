package com.robson.pride.item.weapons;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.WeaponData;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.network.chat.Component;

public interface Pyroscourge {
    WeaponData WEAPON_DATA = new WeaponData(Component.literal("Pyroscourge"), "colossalsword" , 10, 0.5f, 2, 3, 2,
            10, "pride:item/pyroscourge", ServerDataManager.SUN,
            new Matrix2f(-0.5f, -0.5f, -0.15f, 0.2f, 0.8f, 2.6f),
            ServerDataManager.FLAME_SLASH,
            new WeaponData.AttributeReqs('B', 'C', 'S', (byte) 10, (byte) 5, (byte) 10),
            new FixedRGB(255, 50, 0));
}