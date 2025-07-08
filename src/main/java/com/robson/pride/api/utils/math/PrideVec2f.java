package com.robson.pride.api.utils.math;

import net.minecraft.world.phys.Vec3;

public record PrideVec2f(float x, float y) {

    public static PrideVec2f toPlaneVector(PrideVec3f vec3f) {
        return new PrideVec2f(vec3f.x(), vec3f.z());
    }

    public static PrideVec2f toPlaneVector(Vec3 vec3) {
        return new PrideVec2f((float) vec3.x, (float) vec3.z);
    }

    public PrideVec2f scale(float scale) {
        return new PrideVec2f(x * scale, y * scale);
    }

    public PrideVec2f normalize() {
        return this.divide(this.length());
    }

    public PrideVec2f rotate(float degrees) {
        return MathUtils.rotate2DVector(this, degrees);
    }

    public PrideVec2f divide(float divisor) {
        if (divisor == 0) {
            return new PrideVec2f(0, 0);
        }
        return new PrideVec2f(x / divisor, y / divisor);
    }

    public float length() {
        return (float) Math.sqrt((x * x) + (y * y));
    }
}
