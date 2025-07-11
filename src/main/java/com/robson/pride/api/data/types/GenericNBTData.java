package com.robson.pride.api.data.types;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public abstract class GenericNBTData extends GenericData{

    public GenericNBTData(@NotNull CompoundTag tag) {
        super(100 + tag.sizeInBytes());
    }

}
