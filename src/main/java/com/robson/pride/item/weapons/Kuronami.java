package com.robson.pride.item.weapons;

import com.robson.pride.api.data.types.item.WeaponData;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.network.chat.Component;

public interface Kuronami {

    WeaponData WEAPON_DATA = new WeaponData(Component.literal("Kuronami"), "katana", 10, 1f, 2, 3, 2,
            10, "minecraft:item/creationsplitter", ServerDataManager.DARKNESS,
            new Matrix2f(-0.5f, -0.5f, -0.15f, 0.2f, 0.8f, 1.5f),
            ServerDataManager.DARKNESS_CUT,
            new WeaponData.AttributeReqs('B', 'C', 'S', (byte) 10, (byte) 5, (byte) 10),
            new FixedRGB((short) 0, (short) 0, (short) 0));

}

