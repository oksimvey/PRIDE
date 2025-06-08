package com.robson.pride.mixins;

import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import yesman.epicfight.api.utils.math.CubicBezierCurve;

import java.util.ArrayList;
import java.util.List;

@Mixin(CubicBezierCurve.class)
public class CubicBezierCurveMixin {

    private static List<Float> MATRIXF_CONSTANTS = new ArrayList<>();


    private static void getBezierFEquationCoefficients(List<Float> points, List<Float> aList, List<Float> bList) {
        List<Float> results = new ArrayList<>();
        int size = points.size();
        results.add(points.get(0) + points.get(1) * 2.0F);

        for (int idx = 1; idx < size - 2; ++idx) {
            results.add(points.get(idx) * 4.0F + points.get(idx + 1) * 2.0F);
        }

        results.add(points.get(size - 2) * 8.0F + points.get(size - 1));
        int storedConstsSize = MATRIXF_CONSTANTS.size();
        int coordSize = results.size();
        if (storedConstsSize < coordSize - 1) {
            for (int i = 0; i < coordSize - 1 - storedConstsSize; ++i) {
                float lastConst = MATRIXF_CONSTANTS.get(MATRIXF_CONSTANTS.size() - 1);
                MATRIXF_CONSTANTS.add(1.0F / (4.0F - lastConst));
            }
        }

        List<Float> convertedResults = new ArrayList<>();

        for (int idx = 0; idx < coordSize; ++idx) {
            if (idx == 0) {
                convertedResults.add(results.get(idx) * 0.5F);
            } else if (idx == coordSize - 1) {
                convertedResults.add((results.get(idx) - 2.0F * convertedResults.get(idx - 1)) / (7.0F - 2.0F * MATRIXF_CONSTANTS.get(idx - 1)));
            } else {
                convertedResults.add((results.get(idx) - convertedResults.get(idx - 1)) / ((4.0F - MATRIXF_CONSTANTS.get(idx - 1))));
            }
        }

        for (int idx = coordSize - 1; idx >= 0; --idx) {
            if (idx == coordSize - 1) {
                aList.add(0, convertedResults.get(idx));
            } else {
                aList.add(0, convertedResults.get(idx) - aList.get(0) * MATRIXF_CONSTANTS.get(idx));
            }
        }

        for (int i = 0; i < coordSize; ++i) {
            if (i == coordSize - 1) {
                bList.add((aList.get(i) + points.get(i + 1)) * 0.5F);
            } else {
                bList.add(2.0F * points.get(i + 1) - aList.get(i + 1));
            }
        }

    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    private static double cubicBezier(double start, double end, double a, double b, double t) {
        return Math.pow(1.0F - t, 3.0F) * start + 3.0F * t * Math.pow(1.0F - t, 2.0F) * a + 3.0F * t * t * (1.0F - t) * b + t * t * t * end;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static List<Vec3> getBezierInterpolatedPoints(List<Vec3> points, int interpolatedResults) {
        return getBezierInterpolatedPoints(points, 0, points.size() - 1, interpolatedResults);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static List<Vec3> getBezierInterpolatedPoints(List<Vec3> points, int sliceBegin, int sliceEnd, int interpolatedResults) {
        if (points.size() < 3) {
            return null;
        } else {
            sliceBegin = Math.max(sliceBegin, 0);
            sliceEnd = Math.min(sliceEnd, points.size() - 1);
            int size = points.size();
            List<Vec3> interpolatedPoints = new ArrayList<>();
            List<Float> x = new ArrayList<>();
            List<Float> y = new ArrayList<>();
            List<Float> z = new ArrayList<>();

            for (int idx = 0; idx < size; ++idx) {
                x.add((float) ((Vec3) points.get(idx)).x);
                y.add((float) ((Vec3) points.get(idx)).y);
                z.add((float) ((Vec3) points.get(idx)).z);
            }

            List<Float> x_a = new ArrayList<>();
            List<Float> x_b = new ArrayList<>();
            List<Float> y_a = new ArrayList<>();
            List<Float> y_b = new ArrayList<>();
            List<Float> z_a = new ArrayList<>();
            List<Float> z_b = new ArrayList<>();
            getBezierFEquationCoefficients(x, x_a, x_b);
            getBezierFEquationCoefficients(y, y_a, y_b);
            getBezierFEquationCoefficients(z, z_a, z_b);

            for (int i = sliceBegin; i < sliceEnd; ++i) {
                if (!interpolatedPoints.isEmpty()) {
                    interpolatedPoints.remove(interpolatedPoints.size() - 1);
                }

                Vec3 start = (Vec3) points.get(i);
                Vec3 end = (Vec3) points.get(i + 1);
                float x_av = x_a.get(i);
                float x_bv = x_b.get(i);
                float y_av = y_a.get(i);
                float y_bv = y_b.get(i);
                float z_av = z_a.get(i);
                float z_bv = z_b.get(i);

                for (int j = 0; j < interpolatedResults + 1; ++j) {
                    float t = (float) j / (float) interpolatedResults;
                    interpolatedPoints.add(new Vec3(cubicBezier(start.x, end.x, x_av, x_bv, t), cubicBezier(start.y, end.y, y_av, y_bv, t), cubicBezier(start.z, end.z, z_av, z_bv, t)));
                }
            }

            return interpolatedPoints;
        }
    }

    static {
        MATRIXF_CONSTANTS.add(0.5F);
    }
}
