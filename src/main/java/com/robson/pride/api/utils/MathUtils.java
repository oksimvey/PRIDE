package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
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

    public static AABB createAABBByLookingAngle(Vec3 pos, Vector3f lookangle, double radius){
        double length = radius * 2;
        return new AABB(pos.x + lookangle.x * radius - (radius * 1.5),
                pos.y - (radius * 0.5),
                pos.z + lookangle.z * radius - (radius * 2),
                pos.x + lookangle.x * length + (radius * 1.5),
                pos.y + (radius * 1.5),
                pos.z + lookangle.z * length + (radius * 2));
    }

    public static AABB createAABBForCulling(double radius){
       return  createAABBByLookingAngle(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition(), Minecraft.getInstance().gameRenderer.getMainCamera().getLookVector(), radius);
    }

    public static float getAngleDifferencee(Entity ent1, Entity ent2){
        if (ent1 != null && ent2 != null){
            return (float) Math.sqrt(Math.pow(ent1.getYRot(), 2) + Math.pow(ent2.getYRot(), 2));
        }
        return 0;
    }
}
