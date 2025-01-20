package com.robson.pride.api.utils;

import com.robson.pride.entities.special.Shooter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

import java.util.concurrent.TimeUnit;

public class ProjectileUtil {

    public static void shootProjectileFromEnt(Projectile projectile, Entity ent, float speed) {
        if (ent != null) {
            if (!(ent instanceof Player)) {
                TargetUtil.rotateToTarget(ent);
            }
            projectile.setOwner(ent);
            projectile.shootFromRotation(ent, ent.getXRot() - 5, ent.getYRot(), 0.0F, speed, speed / 3);
            projectile.setPos(ent.getLookAngle().scale(1.25).add(0, ent.getBbHeight(), 0).add(ent.position()));
            ent.level().addFreshEntity(projectile);
        }
    }

    public static void shootProjectileFromShooter(Shooter shooter, Projectile projectile, Entity ent, float speed, boolean isnew) {
        if (ent != null) {
            projectile.setOwner(ent);
            projectile.shootFromRotation(shooter, shooter.getXRot() - 10, shooter.getYRot(), 0, speed, speed / 3);
            projectile.setPos(shooter.getLookAngle().scale(1.5).add(shooter.position()));
            if (isnew) {
                ent.level().addFreshEntity(projectile);
            }
            TimerUtil.schedule(() -> shooter.remove(Entity.RemovalReason.DISCARDED), 100, TimeUnit.MILLISECONDS);
        }
    }
}
