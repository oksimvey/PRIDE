package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;

public class TeleportUtils {

    public static void teleportEntityRelativeToEntity(Entity entity1, Entity entity2, double offsetX, double offsetZ) {
         Vec3 vec = entity2.getLookAngle().scale(offsetZ).add(entity2.position());
         double NewX = vec.x ;
         double NewY = entity2.getY();
         double NewZ = vec.z;
         entity1.setPos(NewX, NewY, NewZ);
    }

    public static void teleportEntityToEntityJoint(Entity entity1, Entity entity2, Joint joint, double offsetx, double offsety, double offsetz){
        if (entity1 != null && entity2 != null){
            Vec3 vec3 = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, entity2, joint);
            if (vec3 != null){
                entity1.setPos(vec3.x + (offsetx), vec3.y + (offsety), vec3.z + (offsetz));
            }
        }
    }
}
