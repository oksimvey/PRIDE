package com.robson.pride.api.data.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class DynamicList<A> extends DynamicDataBase<Integer, A, List<DynamicDataParameter<A>>> {

    public DynamicList(DynamicDataParameter.DataType type) {
        super(List.of(), type);
    }

    @Override
    public A get(Integer key) {
        if (DATA.size() <= key) {
            return null;
        }
        return DATA.get(key).getData();
    }

    @Override
    protected void putOnMap(Integer key, DynamicDataParameter<A> data) {
        DATA.add(data);
    }

    @Override
    protected DynamicDataParameter<A> getParameter(Integer key) {
        if (DATA.size() <= key) {
            return null;
        }
        return DATA.get(key);
    }

    @Override
    protected List<Integer> getAllKeys() {
        List<Integer> keyset = new ArrayList<>();
        for (int i = 0; i < DATA.size(); i++) {
            keyset.add(i);
        }
        return keyset;
    }

    @Override
    public void removeKey(Integer key) {
        DATA.remove((int) key);
    }

    @Override
    public void removeValue(DynamicDataParameter<A> value) {
        DATA.remove(value);
    }
}
