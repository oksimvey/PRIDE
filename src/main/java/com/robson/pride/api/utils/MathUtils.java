package com.robson.pride.api.utils;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
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

    private static final List<Float> MATRIX_CONSTANTS = new ArrayList<>();

    private static void getBezierEquationCoefficients(List<Float> points, List<Float> aList, List<Float> bList) {
        List<Float> results = new ArrayList<>();
        int size = points.size();
        results.add(points.get(0) + points.get(1) * 2.0F);
        for(int idx = 1; idx < size - 2; ++idx) {
            results.add(points.get(idx) * 4.0F + points.get(idx + 1) * 2.0F);
        }
        results.add(points.get(size - 2) * 8.0F + points.get(size - 1));
        int storedConstsSize = MATRIX_CONSTANTS.size();
        int coordSize = results.size();
        if (storedConstsSize < coordSize - 1) {
            for(int i = 0; i < coordSize - 1 - storedConstsSize; ++i) {
                float lastConst = MATRIX_CONSTANTS.get(MATRIX_CONSTANTS.size() - 1);
                MATRIX_CONSTANTS.add(1.0F / 4.0F - lastConst);
            }
        }
        List<Float> convertedResults = new ArrayList<>();
        for(int idx = 0; idx < coordSize; ++idx) {
            if (idx == 0) {
                convertedResults.add(results.get(idx) * 0.5F);
            } else if (idx == coordSize - 1) {
                convertedResults.add((results.get(idx) - 2.0F * convertedResults.get(idx - 1)) / (7.0F - 2.0F * MATRIX_CONSTANTS.get(idx - 1)));
            } else {
                convertedResults.add((results.get(idx) - convertedResults.get(idx - 1)) / (4.0F - MATRIX_CONSTANTS.get(idx - 1)));
            }
        }
        for(int idx = coordSize - 1; idx >= 0; --idx) {
            if (idx == coordSize - 1) {
                aList.add(0, (float) convertedResults.get(idx));
            } else {
                aList.add(0, (float) (convertedResults.get(idx) - aList.get(0) * MATRIX_CONSTANTS.get(idx)));
            }
        }

        for(int i = 0; i < coordSize; ++i) {
            if (i == coordSize - 1) {
                bList.add((aList.get(i) + points.get(i + 1)) * 0.5F);
            } else {
                bList.add(2.0F * points.get(i + 1) - aList.get(i + 1));
            }
        }

    }

    private static float cubicBezier(float start, float end, float a, float b, float t) {
        return (float) (Math.pow(1.0F - t, 3.0F) * start + 3.0F * t * Math.pow(1.0F - t, 2.0F) * a + 3.0F * t * t * (1.0F - t) * b + t * t * t * end);
    }

    public static List<Vec3> getBezierInterpolatedPoints(List<Vec3> points, int interpolatedResults) {
        return getBezierInterpolatedPoints(points, 0, points.size() - 1, interpolatedResults);
    }

    public static List<Vec3> getBezierInterpolatedPoints(List<Vec3> points, int sliceBegin, int sliceEnd, int interpolatedResults) {
        if (points.size() < 3) {
            return null;
        } else {
            sliceBegin = Math.max(sliceBegin, 0);
            sliceEnd = Math.min(sliceEnd, points.size() - 1);
            List<Vec3> interpolatedPoints = Lists.newArrayList();
            List<Float> x = new ArrayList<>();
            List<Float> y = new ArrayList<>();
            List<Float> z = new ArrayList<>();
            for (Vec3 point : points) {
                x.add((float) point.x);
                y.add((float) point.y);
                z.add((float) point.z);
            }
            List<Float> x_a = new ArrayList<>();
            List<Float> x_b = new ArrayList<>();
            List<Float> y_a = new ArrayList<>();
            List<Float> y_b = new ArrayList<>();
            List<Float> z_a = new ArrayList<>();
            List<Float> z_b = new ArrayList<>();
            getBezierEquationCoefficients(x, x_a, x_b);
            getBezierEquationCoefficients(y, y_a, y_b);
            getBezierEquationCoefficients(z, z_a, z_b);

            for(int i = sliceBegin; i < sliceEnd; ++i) {
                if (!interpolatedPoints.isEmpty()) {
                    interpolatedPoints.remove(interpolatedPoints.size() - 1);
                }
                Vec3 start = points.get(i);
                Vec3 end = points.get(i + 1);
                float x_av = x_a.get(i);
                float x_bv = x_b.get(i);
                float y_av = y_a.get(i);
                float y_bv = y_b.get(i);
                float z_av = z_a.get(i);
                float z_bv = z_b.get(i);
                for(int j = 0; j < interpolatedResults + 1; ++j) {
                    float t = (float) j / interpolatedResults;
                    interpolatedPoints.add(new Vec3(cubicBezier((float) start.x, (float) end.x, x_av, x_bv, t), cubicBezier((float) start.y, (float) end.y, y_av, y_bv, t), cubicBezier((float) start.z, (float) end.z, z_av, z_bv, t)));
                }
            }

            return interpolatedPoints;
        }
    }

    static {
        MATRIX_CONSTANTS.add(0.5F);
    }
}
