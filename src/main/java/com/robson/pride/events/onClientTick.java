package com.robson.pride.events;

import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.mechanics.Aura;
import com.robson.pride.progression.ElementalUtils;
import com.robson.pride.registries.KeyRegister;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.client.events.engine.ControllEngine;

import java.util.Objects;

@Mod.EventBusSubscriber
public class onClientTick {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null) {

        }
    }
}
