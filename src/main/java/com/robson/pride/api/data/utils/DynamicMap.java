package com.robson.pride.api.data.utils;

import com.robson.pride.api.data.types.GenericData;

public class DynamicMap<A, B extends GenericData> extends DynamicStaticMap<A, B>{

    public DynamicMap() {
        super();
    }

    @Override
    public B get(A key) {
        return super.get(key);
    }
}
