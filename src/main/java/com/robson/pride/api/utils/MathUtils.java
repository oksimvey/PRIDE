package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Random;

public class MathUtils {

    private static Random random = new Random();

    public static float getValueWithPercentageIncrease(double number, double percentage){
        return (float) (number + (number * percentage / 100));
    }

    public static float getValueWithPercentageDecrease(double number, double percentage){
        return (float) (number - (number * percentage / 100));
    }

    public static int getRandomInt(int bound){
        return random.nextInt(bound);
    }

    public static double getTotalDistance(double deltax, double deltay, double deltaz){
        return Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2) + Math.pow(deltaz, 2));
    }

    public static AABB createAABBForCulling(double radius){
        Vector3f lookAngle = Minecraft.getInstance().gameRenderer.getMainCamera().getLookVector();
        Vec3 camerapos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double length = radius * 2;
        return new AABB(camerapos.x + lookAngle.x * radius - (radius * 1.5),
                camerapos.y - (radius * 0.5),
                camerapos.z + lookAngle.z * radius - (radius * 2),
                camerapos.x + lookAngle.x * length + (radius * 1.5),
                camerapos.y + (radius * 1.5),
                camerapos.z + lookAngle.z * length + (radius * 2));
    }


}
