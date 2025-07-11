package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.GenericData;
import com.robson.pride.api.data.utils.DynamicMap;

public abstract class DataManager<B extends GenericData> {

    private final DynamicMap<String, B> DATA = new DynamicMap<>();

    public final B getByKey(String value){
        if (DATA.get(value) == null){
            DATA.put(value, getDefault(value));
        }
        return DATA.get(value);
    }

    protected abstract B getDefault(String value);

}
