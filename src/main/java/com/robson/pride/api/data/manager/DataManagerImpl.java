package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.GenericData;

public interface DataManagerImpl<T extends GenericData> {

    T getByID(short id);
}
