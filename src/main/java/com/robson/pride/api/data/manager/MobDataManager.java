package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.mob.MobGenericData;

public interface MobDataManager extends DataManager<MobGenericData> {

    MobDataManager INSTANCE = id -> switch (id) {
        default -> null;
    };

}
