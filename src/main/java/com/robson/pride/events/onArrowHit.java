package com.robson.pride.events;

import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class onArrowHit {

    public static void onArrowDmg(ProjectileImpactEvent event){
        if (event.getProjectile() instanceof Arrow){

        }
    }
}
