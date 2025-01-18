package com.robson.pride.api.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class ProjectileUtil {

    public static void shootProjectileFromEnt(Projectile projectile, Entity ent, float speed) {
        if (ent != null) {
            if (!(ent instanceof Player)){
                TargetUtil.rotateToTarget(ent);
            }
            projectile.setOwner(ent);
            projectile.shootFromRotation(ent, ent.getXRot(), ent.getYRot(), 0.0F,  speed, speed / 3);
            projectile.setPos(ent.getLookAngle().add(0, ent.getBbHeight(), 0).add(ent.position()));
            ent.level().addFreshEntity(projectile);
        }
    }
}
