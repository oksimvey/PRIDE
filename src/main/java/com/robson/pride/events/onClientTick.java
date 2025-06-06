package com.robson.pride.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robson.pride.api.client.RenderingCore;
import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onClientTick {

    public static PoseStack playerStack = null;

    public static MultiBufferSource playerBuffer = null;

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (!Minecraft.getInstance().isPaused()) {
                RenderingCore.renderCore();
            }
            LocalPlayer player = Minecraft.getInstance().player;
            if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND) && player.isUsingItem()) {
                player.setSprinting(false);
            }
        }
    }

    @SubscribeEvent
    public static void onRender(RenderPlayerEvent.Pre event) {
        if (event != null) {
            playerBuffer = event.getMultiBufferSource();
            playerStack = event.getPoseStack();
        }
    }
}

