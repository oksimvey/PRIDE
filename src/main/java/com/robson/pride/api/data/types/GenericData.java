package com.robson.pride.api.data.types;

public abstract class GenericData {

    private final long size;

    protected GenericData(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}
