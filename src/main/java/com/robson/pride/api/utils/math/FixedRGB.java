package com.robson.pride.api.utils.math;

public record FixedRGB(short r, short g, short b) {

    public FixedRGB(short r, short g, short b) {
        this.r = (short) (255 - r);
        this.g = (short) (255 - g);
        this.b = (short) (255 - b);
    }
}
