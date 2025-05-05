package com.robson.pride.api.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

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

    public static double getTotalDistance(double deltax, double deltay, double deltaz) {
        return Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2) + Math.pow(deltaz, 2));
    }

    public static double getTotalDistance(Vec3 vec1, Vec3 vec2) {
        return getTotalDistance(vec1.x - vec2.x, vec1.y - vec2.y, vec1.z - vec2.z);
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
