package com.robson.pride.api.data.types.entity;

import com.robson.pride.api.data.types.GenericData;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;

public abstract class MobTypeData extends GenericData {

    private final byte maxVariants;

    private final Music mobMusic;

    private final byte music_priority;

    public MobTypeData(String name, byte maxVariants, Music mobMusic, byte musicPriority) {
        super(Component.literal(name), "minecraft:item/spawn_egg", new Matrix2f(-0.1f, -0.1f, -0.1f, 0.1f, 0.1f, 0.1f), (byte) 11, (byte) 1);
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
