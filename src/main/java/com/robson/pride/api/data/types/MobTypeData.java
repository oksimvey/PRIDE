package com.robson.pride.api.data.types;

import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.network.chat.Component;

public abstract class MobTypeData extends GenericData {

    private final byte maxVariants;

    public MobTypeData(String name, byte maxVariants) {
        super(Component.literal(name), "minecraft:item/spawn_egg", new Matrix2f(-0.1f, -0.1f, -0.1f, 0.1f, 0.1f, 0.1f), (byte) 11, (byte) 1);
        this.maxVariants = maxVariants;
    }

    public abstract MobData getDataByVariant(byte variant);

    public byte getMaxVariants(){
        return this.maxVariants;
    }
}
