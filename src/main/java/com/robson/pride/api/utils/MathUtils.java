package com.robson.pride.api.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathUtils {

    private static Random random = new Random();

    public static float getValueWithPercentageIncrease(double number, double percentage) {
        return (float) (number + (number * percentage / 100));
    }

    public static float getValueWithPercentageDecrease(double number, double percentage) {
        return (float) (number - (number * percentage / 100));
    }

    public static int getRandomInt(int bound) {
        return random.nextInt(bound);
    }

    public static float getTotalDistance(double deltax, double deltay, double deltaz) {
        return (float) Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2) + Math.pow(deltaz, 2));
    }

    public static float getTotalDistance(Vec3 vec1, Vec3 vec2) {
        return getTotalDistance(vec1.x - vec2.x, vec1.y - vec2.y, vec1.z - vec2.z);
    }

    public static float degreeToRadians(float degree){
        return 3.14f * (degree / 180);
    }

    public static Vec3 rotate2DVector(Vec3 vec, float degrees){
        float theta = degreeToRadians(degrees);
        float x = (float) ((vec.x * Math.cos(theta)) - (vec.z * Math.sin(theta)));
        float z = (float) ((vec.x * Math.sin(theta)) + (vec.z * Math.cos(theta)));
        return new Vec3(x, vec.y, z);
    }

    public static List<Vec3> getVectorsForHorizontalCircle(Vec3 radiusvec, int points){
        List<Vec3> circlevecs = new ArrayList<>();
        if (radiusvec != null){
            for (int i = 0; i <= 360; i+= 360 / points){
                circlevecs.add(rotate2DVector(radiusvec, i));
            }
        }
        return circlevecs;
    }

    public static List<Vec3> getVectorsForHorizontalSpiral(Vec3 radiusvec, byte larps, int points, int scale){
        List<Vec3> circlevecs = new ArrayList<>();
        if (radiusvec != null){
            int totalangle = 360 * larps;
            for (int i = 0; i <= totalangle; i+= totalangle / points){
                circlevecs.add(rotate2DVector(radiusvec.scale((double) (i * scale) / totalangle), i));
            }
        }
        return circlevecs;
    }


    public static float setDecimalsOnFloat(float number, byte decimals){
        int amount = (int) Math.pow(10, decimals);
        int newnumber = (int) (number * amount);
        return (float) newnumber / amount;
    }

    public static AABB createAABBAroundEnt(Entity ent, float size) {
        return new AABB(ent.getX() + size, ent.getY() + size * 1.5, ent.getZ() + size, ent.getX() - size, ent.getY() - size, ent.getZ() - size);
    }
}
