package com.robson.pride.events;

import com.robson.pride.mechanics.MikiriCounter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class onArrowHit {

    public static void onArrowDmg(ProjectileImpactEvent event){
        if (event.getProjectile() instanceof Arrow && event.getEntity()!=null){
            if (event.getEntity().getPersistentData().getString("Mikiri").equals("Dodge"));
            MikiriCounter.onArrowMikiri(event.getEntity(), event.getProjectile(), event);
        }
    }
}
