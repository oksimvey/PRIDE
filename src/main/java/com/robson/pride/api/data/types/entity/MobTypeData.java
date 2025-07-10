package com.robson.pride.api.data.types.entity;

import com.robson.pride.api.data.types.GenericItemData;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;

public abstract class MobTypeData extends GenericItemData {

    private final byte maxVariants;

    private final Music mobMusic;

    private final byte music_priority;

    public MobTypeData(CompoundTag tag, String name, byte maxVariants, Music mobMusic, byte musicPriority) {
        super(tag);
        this.maxVariants = maxVariants;
        this.mobMusic = mobMusic;
        music_priority = musicPriority;
    }

    public abstract MobData getDataByVariant(byte variant);

    public byte getMaxVariants(){
        return this.maxVariants;
    }

    public byte getMusicPriority(){
        return this.music_priority;
    }

    public Music getMobMusic(){
        return this.mobMusic;
    }
}
