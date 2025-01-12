package com.robson.pride.api.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

public class ProjectileUtil {

    public static void shootProjectileFromEnt(Projectile projectile, Entity ent, float speed){
        Entity owner  = projectile.getOwner();
        projectile.setOwner(ent);
        projectile.setPos(ent.getEyePosition().add(ent.position()));
        projectile.setYRot(owner.getYRot() - 180);
        projectile.setDeltaMovement(new Vec3(0, 0, speed));
    }
}
