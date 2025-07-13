package com.robson.pride.api.data.utils;

import com.robson.pride.api.data.types.GenericData;

public class DynamicStaticMap<A, B extends GenericData> extends DynamicDataBase<A, B> {

    public DynamicStaticMap() {
        super(DynamicDataParameter.DataType.GENERIC_DATA);
    }

}
