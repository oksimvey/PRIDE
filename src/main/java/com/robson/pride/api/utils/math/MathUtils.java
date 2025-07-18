package com.robson.pride.api.utils.math;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public interface MathUtils {

    List<Character> NUMBER_CHARS = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    double GRAVITY_CONSTANT = -0.0784000015258789;

    float PI = 3.141592f;

    float EULER = 2.7182818f;

    short CORRECT_HALF = 180;

    byte CORRECT_QUARTER = 45;

    static float getValueWithPercentageIncrease(double number, double percentage) {
        ThreadLocalRandom.current();
        return (float) (number + (number * percentage / 100));
    }

    static float getValueWithPercentageDecrease(float number, float percentage) {
        return number - (number * percentage / 100);
    }


    static int getRandomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    static float getTotalDistance(Vec3 vec1, Vec3 vec2) {
        return getTotalDistance(PrideVec3f.fromVec3(vec1), PrideVec3f.fromVec3(vec2));
    }

    static float getTotalDistance(float deltax, float deltay, float deltaz) {
        return (float) Math.sqrt((deltax * deltax) + (deltay * deltay) + (deltaz * deltaz));
    }

    static float getTotalDistance(PrideVec3f vec1, PrideVec3f vec2) {
        return getTotalDistance(vec1.x() - vec2.x(), vec1.y() - vec2.y(), vec1.z() - vec2.z());
    }

    static float degreeToRadians(float degree) {
        return PI * (degree / 180f);
    }

    static Vec3 rotate2DVector(Vec3 vec, float degrees) {
        float theta = degreeToRadians(degrees);
        float x = (float) ((vec.x * Math.cos(theta)) - (vec.z * Math.sin(theta)));
        float z = (float) ((vec.x * Math.sin(theta)) + (vec.z * Math.cos(theta)));
        return new Vec3(x, vec.y, z);
    }

    static PrideVec2f rotate2DVector(PrideVec2f vec, float degrees) {
        float theta = degreeToRadians(degrees);
        float x = (float) ((vec.x() * Math.cos(theta)) - (vec.y() * Math.sin(theta)));
        float z = (float) ((vec.x() * Math.sin(theta)) + (vec.y() * Math.cos(theta)));
        return new PrideVec2f(x, z);
    }

    static PrideVec3f rotate2DVector(PrideVec3f vec, float degrees) {
        float theta = degreeToRadians(degrees);
        float x = (float) ((vec.x() * Math.cos(theta)) - (vec.z() * Math.sin(theta)));
        float z = (float) ((vec.x() * Math.sin(theta)) + (vec.z() * Math.cos(theta)));
        return new PrideVec3f(x, vec.y(), z);
    }

    static List<PrideVec3f> getVectorsForHorizontalCircle(PrideVec3f radiusvec, int points) {
        List<PrideVec3f> circlevecs = new ArrayList<>();
        if (radiusvec != null) {
            for (int i = 0; i <= 360; i += 360 / points) {
                circlevecs.add(rotate2DVector(radiusvec, i));
            }
        }
        return circlevecs;
    }


    static List<PrideVec3f> getVectorsForHorizontalSpiral(PrideVec3f radiusvec, byte larps, int points, int scale) {
        List<PrideVec3f> circlevecs = new ArrayList<>();
        if (radiusvec != null) {
            int totalangle = 360 * larps;
            for (int i = 0; i <= totalangle; i += totalangle / points) {
                circlevecs.add(rotate2DVector(radiusvec.scale((float) (i * scale) / totalangle), i));
            }
        }
        return circlevecs;
    }


    static float setDecimalsOnFloat(float number, byte decimals) {
        int amount = (int) Math.pow(10, decimals);
        int newnumber = (int) (number * amount);
        return (float) newnumber / amount;
    }

    static AABB createAABBAroundEnt(Entity ent, float size) {
        return new AABB(ent.getX() + size, ent.getY() + size * 1.5, ent.getZ() + size, ent.getX() - size, ent.getY() - size, ent.getZ() - size);
    }

}
