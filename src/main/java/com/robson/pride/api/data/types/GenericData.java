package com.robson.pride.api.data.types;

public abstract class GenericData {

    private final int size;

    protected GenericData(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
