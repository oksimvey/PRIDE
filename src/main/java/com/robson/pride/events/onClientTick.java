package com.robson.pride.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onClientTick {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null) {
        }
    }
}
