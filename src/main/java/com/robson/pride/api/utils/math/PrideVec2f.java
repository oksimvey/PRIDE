package com.robson.pride.api.utils.math;

import net.minecraft.world.phys.Vec3;

public class PrideVec2f extends PrideVector<PrideVec2f> {

    public PrideVec2f(float x, float y) {
        super(x, y);
    }

    @Override
    protected PrideVec2f self(float... values) {
        return new PrideVec2f(values[0], values[1]);
    }

    public static PrideVec2f toPlaneVector(PrideVec3f vec3f){
        return new PrideVec2f(vec3f.x(), vec3f.z());
    }

    public static PrideVec2f toPlaneVector(Vec3 vec3){
        return new PrideVec2f((float) vec3.x, (float) vec3.z);
    }

    public float x(){
        return this.dimensions[0];
    }

    public float y(){
        return this.dimensions[1];
    }
}
