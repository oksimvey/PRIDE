package com.robson.pride.events;

import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onClientTick {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null) {
            ParticleTracking.auraImbuementTrackingCore();
            }
        Player player = Minecraft.getInstance().player;
        if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND) && player.isUsingItem()) {
               player.setSprinting(false);
        }
        }
    }

