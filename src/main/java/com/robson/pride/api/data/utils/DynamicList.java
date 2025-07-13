package com.robson.pride.api.data.utils;

public class DynamicList<A> extends DynamicMap<A, Boolean> {

    public DynamicList(DynamicDataParameter.DataType type) {
        super(type, DynamicDataParameter.DataType.BOOLEAN);
    }

    public void add(A a){
        super.put(a, true);
    }
}
