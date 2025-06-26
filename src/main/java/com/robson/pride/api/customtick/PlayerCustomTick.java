package com.robson.pride.api.customtick;

import com.robson.pride.api.cam.DynamicCam;
import com.robson.pride.api.data.player.ClientData;
import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.musiccore.MusicCore;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.epicfight.styles.SheatProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.data.player.ClientDataManager.CLIENT_DATA_MANAGER;

public class PlayerCustomTick {

    public static void startTick(Player player) {
        stopTick(player);
        CLIENT_DATA_MANAGER.put(player, ClientData.createDefault(player));
        loopTick(player);
    }

    public static void stopTick(Player player) {
        ClientDataManager.CLIENT_DATA_MANAGER.remove(player);
    }
    public static void onTick(Player player) {
        if (CLIENT_DATA_MANAGER.get(player) != null) {
            loopTick(player);
            if (!Minecraft.getInstance().isPaused()) {
                CLIENT_DATA_MANAGER.get(player).tick(player);
                if (player.tickCount % 10 == 0) {
                    SheatProvider.provideSheat(player);
                    MusicCore.musicCore(player);
                    DynamicCam.dynamicCamTick(player);
                }
            }
        }
    }

    public static void startRespawnTick(Player player) {
        stopTick(player);
        TimerUtil.schedule(() -> startTick(player), 52, TimeUnit.MILLISECONDS);
    }

    public static void loopTick(Player player) {
        TimerUtil.schedule(() -> onTick(player), 50, TimeUnit.MILLISECONDS);
    }
}
