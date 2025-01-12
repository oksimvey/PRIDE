package com.robson.pride.events;

import com.robson.pride.api.utils.CustomTick;
import com.robson.pride.api.utils.StaminaUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerSetup {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            StaminaUtils.resetStamina(player);
            player.getPersistentData().putBoolean("isParrying", false);
            CustomTick.startTick(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            CustomTick.startRespawnTick(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            CustomTick.stopTick(player);
        }
    }
}
