package com.robson.pride.api.data.types.entity;

import com.robson.pride.api.data.types.GenericNBTData;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class MobData extends GenericNBTData {

    public MobData(@NotNull CompoundTag tag) {
        super(tag);
    }

}
