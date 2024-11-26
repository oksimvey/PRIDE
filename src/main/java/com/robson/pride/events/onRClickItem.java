package com.robson.pride.events;

import com.robson.pride.mechanics.Parry;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onRClickItem {
    @SubscribeEvent
    public static void onRClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity() != null) {
            Entity ent = event.getEntity();
            Parry.ParryWindow(ent);
        }
    }
}