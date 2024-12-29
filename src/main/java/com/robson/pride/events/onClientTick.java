package com.robson.pride.events;

import com.robson.pride.mechanics.LeavesPhysics;
import com.robson.pride.mechanics.ParticleTracking;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onClientTick {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null) {
            LeavesPhysics.spawnLeaves(Minecraft.getInstance().player);
            ParticleTracking.auraImbuementTrackingCore();
        }
    }
}
