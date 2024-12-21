package com.robson.pride.api.utils;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

import java.util.concurrent.TimeUnit;

public class HealthUtils {

    public static void HealthReset(LivingEntity ent){
        if (ent != null){
            ent.setHealth(ent.getMaxHealth());
        }
    }

    public static void hurtEntity(Entity ent, float amount, DamageSource dmg){
        TimerUtil.schedule(()  -> {
            if (ent != null){
                    ent.hurt(dmg, amount);
            }
        }, 10, TimeUnit.MILLISECONDS);
    }
}
