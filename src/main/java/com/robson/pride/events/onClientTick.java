package com.robson.pride.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robson.pride.api.client.RenderingCore;
import com.robson.pride.api.entity.PrideMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onClientTick {

    public static PoseStack playerStack = null;

    public static MultiBufferSource playerBuffer = null;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void renderEntity(RenderLivingEvent.Pre event) {
        if (!Minecraft.getInstance().isPaused()) {
            if (event.getEntity() instanceof Player || event.getEntity() instanceof PrideMob) {
                RenderingCore.entityRenderer(event.getEntity());
            }
        }
    }
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (playerStack != event.getPoseStack() && playerBuffer != event.getMultiBufferSource()) {
            playerBuffer = event.getMultiBufferSource();
            playerStack = event.getPoseStack();
        }
    }
}

