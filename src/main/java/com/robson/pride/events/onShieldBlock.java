package com.robson.pride.events;

import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onShieldBlock {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBlock(ShieldBlockEvent event){
        event.setCanceled(true);
    }
}

