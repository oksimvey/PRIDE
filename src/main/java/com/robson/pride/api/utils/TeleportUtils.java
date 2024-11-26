package com.robson.pride.api.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class TeleportUtils {

    public static void teleportEntityRelativeToEntity(Entity entity1, Entity entity2, double offsetX, double offsetZ) {
         Vec3 vec = entity2.getLookAngle().scale(offsetZ).add(entity2.position());
         double NewX = vec.x;
         double NewY = entity2.getY();
         double NewZ = vec.z;
         entity1.setPos(NewX, NewY, NewZ);
    }
}
