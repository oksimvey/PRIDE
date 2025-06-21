package com.robson.pride.api.utils.math;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface MathUtils {

    Random random = new Random();

    static float getValueWithPercentageIncrease(double number, double percentage) {
        return (float) (number + (number * percentage / 100));
    }

    static float getValueWithPercentageDecrease(double number, double percentage) {
        return (float) (number - (number * percentage / 100));
    }

    static Vec3f getDeltaForVectors(Vec3f vec1, Vec3f vec2, float speed) {
        return getDeltaForVectors(getTotalDistance(vec1, vec2), vec1, vec2, speed);
    }

    static Vec3f getDeltaForVectors(float distance, Vec3f vec1, Vec3f vec2, float speed) {
        return vec1.add(vec2.subtract(vec1).divide(distance).scale(speed));
    }

    static int getRandomInt(int bound) {
        return random.nextInt(bound);
    }

    static float getTotalDistance(Vec3 vec1, Vec3 vec2) {
        return getTotalDistance(Vec3f.fromVec3(vec1), Vec3f.fromVec3(vec2));
    }

    static float getTotalDistance(float deltax, float deltay, float deltaz) {
        return (float) Math.sqrt((deltax * deltax) + (deltay * deltay) + (deltaz * deltaz));
    }

    static float getTotalDistance(Vec3f vec1, Vec3f vec2) {
        return getTotalDistance(vec1.x() - vec2.x(), vec1.y() - vec2.y(), vec1.z() - vec2.z());
    }

    static float degreeToRadians(float degree) {
        return 3.14f * (degree / 180);
    }

    static Vec3 rotate2DVector(Vec3 vec, float degrees) {
        float theta = degreeToRadians(degrees);
        float x = (float) ((vec.x * Math.cos(theta)) - (vec.z * Math.sin(theta)));
        float z = (float) ((vec.x * Math.sin(theta)) + (vec.z * Math.cos(theta)));
        return new Vec3(x, vec.y, z);
    }

    static Vec3f rotate2DVector(Vec3f vec, float degrees) {
        float theta = degreeToRadians(degrees);
        float x = (float) ((vec.x() * Math.cos(theta)) - (vec.z() * Math.sin(theta)));
        float z = (float) ((vec.x() * Math.sin(theta)) + (vec.z() * Math.cos(theta)));
        return new Vec3f(x, vec.y(), z);
    }

    static List<Vec3f> getVectorsForHorizontalCircle(Vec3f radiusvec, int points) {
        List<Vec3f> circlevecs = new ArrayList<>();
        if (radiusvec != null) {
            for (int i = 0; i <= 360; i += 360 / points) {
                circlevecs.add(rotate2DVector(radiusvec, i));
            }
        }
        return circlevecs;
    }


    static List<Vec3f> getVectorsForHorizontalSpiral(Vec3f radiusvec, byte larps, int points, int scale) {
        List<Vec3f> circlevecs = new ArrayList<>();
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
