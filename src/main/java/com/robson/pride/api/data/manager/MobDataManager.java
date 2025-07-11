package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.entity.MobTypeData;

public interface MobDataManager {

    DataManager<MobTypeData> MANAGER = new DataManager<>() {
        @Override
        protected MobTypeData getDefault(String value) {
            if (DataFileManager.validItem(DataFileManager.ENTITIES, value)) {
                return new MobTypeData(DataFileManager.getEntityData(value));
            }
            return null;
        }
    };
}
