package com.robson.pride.api.utils.math;

import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public record PrideVec3f(float x, float y, float z) {

    public static PrideVec3f fromVec3(Vec3 vec3) {
        return new PrideVec3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public PrideVec3f getDirectionToVector(PrideVec3f vector){
        return vector.subtract(this).normalize();
    }

    public PrideVec3f add(PrideVec3f vec3) {
        return new PrideVec3f(x + vec3.x, y + vec3.y, z + vec3.z);
    }

    public PrideVec3f scale(float scale) {
        return new PrideVec3f(x * scale, y * scale, z * scale);
    }

    public PrideVec3f subtract(PrideVec3f vec3) {
        return new PrideVec3f(x - vec3.x, y - vec3.y, z - vec3.z);
    }

    public float distanceTo(PrideVec3f vec3){
        float distancex = vec3.x - x;
        float distancey = vec3.y - y;
        float distancez = vec3.z - z;
        return (float) Math.sqrt((distancex * distancex) + (distancey * distancey) + (distancez * distancez));
    }

    public boolean willHit(PrideVec3f origin, PrideVec3f point, float baseradius, float heightradius) {
        PrideVec3f direction = this.normalize();
        float distanceToPoint = origin.distanceTo(point);
        PrideVec3f scaled = direction.scale(distanceToPoint);
        return origin.add(scaled).distanceTo(point) < baseradius + heightradius;
    }

    public PrideVec3f normalize(){
        return this.divide(length());
    }

    public float length(){
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public PrideVec3f divide(float divisor){
        if (divisor == 0) {
            return new PrideVec3f(0, 0, 0);
        }
        return new PrideVec3f(x / divisor, y / divisor, z / divisor);
    }

    public PrideVec3f rotate(float degrees) {
        return MathUtils.rotate2DVector(this, degrees);
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }

    public Vector3f toVector3f() {
        return new Vector3f(x, y, z);
    }

    public PrideVec3f add(float x, float y, float z) {
        return new PrideVec3f(this.x - x, this.y - y, this.z - z);
    }
}
