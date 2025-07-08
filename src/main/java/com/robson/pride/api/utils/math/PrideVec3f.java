package com.robson.pride.api.utils.math;

import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.OpenMatrix4f;

public class PrideVec3f extends PrideVector<PrideVec3f>  {

    public PrideVec3f(float x, float y, float z) {
        super(x, y, z);
    }

    @Override
    protected PrideVec3f self(float... values) {
        return new PrideVec3f(values[0], values[1], values[2]);
    }

    public static PrideVec3f fromVec3(Vec3 vec3) {
        return new PrideVec3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public static PrideVec3f fromTranslatedMatrix(OpenMatrix4f matrix4f, float x, float y, float z){
        float x1 = matrix4f.m30 + matrix4f.m00 * x + matrix4f.m10 * y + matrix4f.m20 * z;
        float y1 = matrix4f.m31 + matrix4f.m01 * x + matrix4f.m11 * y + matrix4f.m21 * z;
        float z1 = matrix4f.m32 + matrix4f.m02 * x + matrix4f.m12 * y + matrix4f.m22 * z;
        return new PrideVec3f(x1, y1, z1);

    }

    public static PrideVec3f fromMatrix(OpenMatrix4f matrix4f){
        return new PrideVec3f(matrix4f.m30, matrix4f.m31, matrix4f.m32);
    }

    public float x(){
        return this.dimensions[0];
    }

    public float y(){
        return this.dimensions[1];
    }

    public float z(){
        return this.dimensions[2];
    }

    public Vec3 toVec3() {
        return new Vec3(x(), y(), z());
    }

    public PrideVec3f getDirectionToVector(PrideVec3f vector){
        return vector.subtract(this).normalize();
    }

    public float distanceTo(PrideVec3f vec3){
        float distancex = vec3.x() - x();
        float distancey = vec3.y() - y();
        float distancez = vec3.z() - z();
        return (float) Math.sqrt((distancex * distancex) + (distancey * distancey) + (distancez * distancez));
    }

    public boolean willHit(PrideVec3f origin, PrideVec3f point, float baseradius, float heightradius) {
        PrideVec3f direction = this.normalize();
        float distanceToPoint = origin.distanceTo(point);
        PrideVec3f scaled = direction.scale(distanceToPoint);
        return origin.add(scaled).distanceTo(point) < baseradius + heightradius;
    }
}
