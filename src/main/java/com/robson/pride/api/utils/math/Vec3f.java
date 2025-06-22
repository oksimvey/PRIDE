package com.robson.pride.api.utils.math;

import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public record Vec3f(float x, float y, float z) {

    public static Vec3f fromVec3(Vec3 vec3) {
        return new Vec3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public Vec3f getDirectionToVector(Vec3f vector){
        return vector.subtract(this).normalize();
    }

    public Vec3f add(Vec3f vec3) {
        return new Vec3f(x + vec3.x, y + vec3.y, z + vec3.z);
    }

    public Vec3f scale(float scale) {
        return new Vec3f(x * scale, y * scale, z * scale);
    }

    public Vec3f subtract(Vec3f vec3) {
        return new Vec3f(x - vec3.x, y - vec3.y, z - vec3.z);
    }

    public float distanceTo(Vec3f vec3){
        float distancex = vec3.x - x;
        float distancey = vec3.y - y;
        float distancez = vec3.z - z;
        return (float) Math.sqrt((distancex * distancex) + (distancey * distancey) + (distancez * distancez));
    }

    public boolean willHit(Vec3f origin, Vec3f point, float radius){
        float distanceToPoint = origin.distanceTo(point) ;
        Vec3f scaledDelta = this.scale(distanceToPoint);
        return scaledDelta.distanceTo(point) <= radius;
    }

    public Vec3f normalize(){
        return this.divide(length());
    }

    public float length(){
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public Vec3f divide(float divisor){
        if (divisor == 0) {
            return new Vec3f(0, 0, 0);
        }
        return new Vec3f(x / divisor, y / divisor, z / divisor);
    }

    public Vec3f rotate(float degrees) {
        return MathUtils.rotate2DVector(this, degrees);
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }

    public Vector3f toVector3f() {
        return new Vector3f(x, y, z);
    }
}
