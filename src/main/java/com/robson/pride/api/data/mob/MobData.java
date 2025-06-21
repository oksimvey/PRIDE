package com.robson.pride.api.data.mob;

public abstract class MobData {

    private byte maxVariants;

    public abstract MobData getDataByVariant(byte variant);

}
