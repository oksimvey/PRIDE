package com.robson.pride.events;

import com.robson.pride.api.customtick.PlayerCustomTick;
import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.data.player.ClientSavedData;
import com.robson.pride.api.data.utils.DynamicDataBase;
import com.robson.pride.progression.PlayerAttributeSetup;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
            PlayerCustomTick.startTick(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            PlayerCustomTick.startRespawnTick(player);
            PlayerAttributeSetup.setupPlayerAttributes(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer player1) {
            CompoundTag data = ClientSavedData.toNBT(ClientDataManager.CLIENT_DATA_MANAGER.get(player).getProgressionData());
            ClientDataManager.savePlayerDat(player1, data);
            MinecraftServer level = player1.getServer();
            if (level != null && level.getPlayerCount() <= 1) {
                DynamicDataBase.clearAll();
            }
        }
        if (player != null) {
            PlayerCustomTick.stopTick(player);
        }
    }
}
