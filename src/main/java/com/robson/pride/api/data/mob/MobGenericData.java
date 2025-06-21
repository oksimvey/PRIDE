package com.robson.pride.api.data.mob;

import com.robson.pride.api.data.manager.DataManager;

public abstract class MobGenericData {

    public MobGenericData() {
    }

    public interface MobGenericDataBuilder extends DataManager<MobData>{}
}
