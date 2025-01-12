package com.robson.pride.events;

import com.robson.pride.api.mechanics.MikiriCounter;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class onArrowHit {
    @SubscribeEvent
    public static void onArrowDmg(ProjectileImpactEvent event){
        if (event.getProjectile() instanceof Arrow && event.getEntity()!=null) {
            if (event.getEntity().getPersistentData().getBoolean("mikiri_dodge")) {
                MikiriCounter.onArrowMikiri(event.getEntity(), event.getProjectile(), event);
            }
        }
    }
}
