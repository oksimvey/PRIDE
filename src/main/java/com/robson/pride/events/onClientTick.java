package com.robson.pride.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robson.pride.api.client.RenderScreens;
import com.robson.pride.api.client.RenderingCore;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.keybinding.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onClientTick {

    public static PoseStack playerStack = null;

    public static MultiBufferSource playerBuffer = null;

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null) {
            RenderScreens.renderPlayerScreens(Minecraft.getInstance());
            Player player = Minecraft.getInstance().player;
            if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND) && player.isUsingItem() && player.canSprint()) {
                player.setSprinting(false);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (event != null) {
            playerBuffer = event.getMultiBufferSource();
            playerStack = event.getPoseStack();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderEntity(RenderLivingEvent.Pre event) {
        Minecraft client = Minecraft.getInstance();
        if (!client.isPaused()) {
            if (event.getEntity() instanceof Player player) {
                RenderingCore.entityRenderer(player);
            } else if (event.getEntity() instanceof PrideMobBase prideMobBase && prideMobBase.tickLod(client, 1) && client.levelRenderer.getFrustum().isVisible(prideMobBase.getBoundingBox())) {
                RenderingCore.entityRenderer(prideMobBase);
            }
        }
    }
}

