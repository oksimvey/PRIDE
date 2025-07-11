package com.robson.pride.api.data.types.entity;

import com.robson.pride.api.data.types.item.GenericItemData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.Music;

public class MobTypeData extends GenericItemData {

    private final byte maxVariants;

    public MobTypeData(CompoundTag tag) {
        super(tag);
        this.maxVariants = 0;
    }

    public MobData2 getDataByVariant(byte variant){
        return null;
    }

    public byte getMaxVariants(){
        return this.maxVariants;
    }

}
