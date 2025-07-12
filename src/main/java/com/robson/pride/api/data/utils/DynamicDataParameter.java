package com.robson.pride.api.data.utils;

import com.robson.pride.api.data.types.GenericData;

public class DynamicDataParameter<A> {

    private static final byte BYTE_MODIFIER = 5;

    public enum DataType {
        STRING,
        INTEGER,
        FLOAT,
        SHORT,
        GENERIC_DATA,
        ITEM_STACK,
        ENTITY,
        VECTORS
    }

    public final A data;

    public int accesses;

    public long lastUpdate;

    private final int size;

    public DynamicDataParameter(A data, DataType type) {
        this.data = data;
        this.accesses = 0;
        this.lastUpdate = System.currentTimeMillis();
        this.size = switch (type){

            case STRING -> ((String) data).length() << BYTE_MODIFIER;

            case GENERIC_DATA -> ((GenericData) data).getSize();

            case SHORT -> 16 << BYTE_MODIFIER;

            case INTEGER, FLOAT, VECTORS -> 32 << BYTE_MODIFIER;

            case ITEM_STACK -> 64 << BYTE_MODIFIER;

            case ENTITY -> 128 << BYTE_MODIFIER;

        };
    }

    public int getExpireTime() {
        return size;
    }

    public A getData() {
        accesses++;
        return data;
    }

    public void resetAccesses() {
        this.lastUpdate = System.currentTimeMillis();
        this.accesses = 0;
    }
}
