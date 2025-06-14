package com.robson.pride.api.utils.math;

import net.minecraft.world.phys.Vec3;

public class Vec3f {

    public float x, y, z;

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3f fromVec3(Vec3 vec3){
        return new Vec3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public Vec3f add(Vec3f vec3){
        return new Vec3f(x + vec3.x, y + vec3.y, z + vec3.z);
    }

    public Vec3f scale(float scale){
        return new Vec3f(x * scale, y * scale, z * scale);
    }

    public Vec3f subtract(Vec3f vec3){
        return new Vec3f(x - vec3.x, y - vec3.y, z - vec3.z);
    }

    public Vec3f rotate(float degrees){
        return MathUtils.rotate2DVector(this, degrees);
    }

    public Vec3 toVec3(){
        return new Vec3(x, y, z);
    }


}
