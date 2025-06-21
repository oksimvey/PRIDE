package com.robson.pride.api.utils.math;

public record FixedRGB(int r, int g, int b) {

    public FixedRGB(int r, int g, int b) {
        this.r = 255 - r;
        this.g = 255 - g;
        this.b = 255 - b;
    }
}
