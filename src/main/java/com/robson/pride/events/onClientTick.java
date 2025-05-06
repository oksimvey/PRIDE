package com.robson.pride.events;

import com.robson.pride.api.client.RenderingCore;
import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onClientTick {

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
}

