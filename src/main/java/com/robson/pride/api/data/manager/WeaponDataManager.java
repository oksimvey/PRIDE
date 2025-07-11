package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.item.WeaponData;

public interface WeaponDataManager {

    GenericItemDataManager<WeaponData> MANAGER = new GenericItemDataManager<>() {

        @Override
        protected WeaponData getDefault(String value) {
            if (DataFileManager.validItem(DataFileManager.WEAPONS, value)) {
                return new WeaponData(DataFileManager.getWeaponData(value));
            }
            return null;
        }

    };
}
