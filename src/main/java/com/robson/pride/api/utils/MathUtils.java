package com.robson.pride.api.utils;

public class MathUtils {

    public static float getValueWithPercentageIncrease(double number, double percentage){
        return (float) (number + (number * percentage / 100));
    }

    public static float getValueWithPercentageDecrease(double number, double percentage){
        return (float) (number - (number * percentage / 100));
    }

    public static double getTotalSpeed(double deltax, double deltay, double deltaz){
        return Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2) + Math.pow(deltaz, 2));
    }
}
