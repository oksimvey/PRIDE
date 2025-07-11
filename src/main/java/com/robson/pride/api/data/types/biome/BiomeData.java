package com.robson.pride.api.data.types.biome;

import com.robson.pride.api.data.types.GenericNBTData;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class BiomeData extends GenericNBTData {
    public BiomeData(@NotNull CompoundTag tag) {
        super(tag);
    }
}
