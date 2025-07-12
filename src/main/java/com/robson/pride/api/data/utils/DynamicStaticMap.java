package com.robson.pride.api.data.utils;

import com.robson.pride.api.data.types.GenericData;

import java.util.List;
import java.util.concurrent.*;

public class DynamicStaticMap<A, B extends GenericData> extends DynamicDataBase<A, B, ConcurrentHashMap<A, DynamicDataParameter<B>>> {

    public DynamicStaticMap() {
        super(new ConcurrentHashMap<>(), DynamicDataParameter.DataType.GENERIC_DATA);
    }

    @Override
    protected DynamicDataParameter<B> getParameter(A key) {
        return DATA.get(key);
    }

    @Override
    public B get(A key) {
        DynamicDataParameter<B> data = getParameter(key);
        if (data != null) {
            return data.getData();
        }
        return null;
    }

    @Override
    protected void clear(A key) {
        DATA.get(key).resetAccesses();
    }

    @Override
    protected void putOnMap(A key, DynamicDataParameter<B> data) {
        DATA.put(key, data);
    }

    @Override
    protected List<A> getAllKeys() {
        return DATA.keySet().stream().toList();
    }

    @Override
    public void removeKey(A key) {
        DATA.remove(key);
    }

    @Override
    public void removeValue(DynamicDataParameter<B> value) {
        DATA.entrySet().removeIf(entry -> entry.getValue().equals(value));
    }
}
