package com.robson.pride.mechanics;

import net.minecraft.world.entity.Entity;

public class Stealth {

    public static boolean canBackStab(Entity ent, Entity target){
        if (ent != null && target != null){
            float angledifference = ent.getYRot() - target.getYRot();
            return angledifference > -45 && angledifference < 45;
        }
        return false;
    }
}
