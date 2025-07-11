package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.item.GenericItemData;

public interface ItemDataManager {

    GenericItemDataManager<GenericItemData> MANAGER = new GenericItemDataManager<>() {

        @Override
        protected GenericItemData getDefault(String value) {
            if (DataFileManager.validItem(DataFileManager.ITEMS, value)) {
                return new GenericItemData(DataFileManager.getGenericItemData(value));
            }
            return null;
        }
    };

}
