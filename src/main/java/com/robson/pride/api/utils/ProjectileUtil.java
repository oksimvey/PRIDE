package com.robson.pride.api.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;

public class ProjectileUtil {

    public static void shootProjectileFromEnt(Projectile projectile, Entity ent, float speed){
        projectile.setOwner(ent);
        projectile.setPos(ent.getEyePosition());
        projectile.setOwner(ent);
        projectile.setDeltaMovement(ent.getLookAngle().scale(speed));
    }
}
