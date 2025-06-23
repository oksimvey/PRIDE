package com.robson.pride.api.utils.math;

public record IDHandler(short id) {

    public short getId() {
        return this.id;
    }
}
