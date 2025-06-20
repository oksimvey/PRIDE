package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.MobData;

public interface MobDataManager {

    static MobData getByID(short id){
        return switch (id){
            default -> null;
        };
    }
}
