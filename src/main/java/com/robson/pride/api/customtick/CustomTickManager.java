package com.robson.pride.api.customtick;

import com.robson.pride.api.cam.DynamicCam;
import com.robson.pride.api.client.AutoBattleMode;
import com.robson.pride.api.musiccore.MusicCore;
import com.robson.pride.api.musiccore.PrideMusicManager;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.epicfight.styles.SheatProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.musiccore.PrideMusicManager.playerMusicManagerThread;

public class CustomTickManager {

    public static void startTick(Player player) {
        stopTick(player);
       playerMusicManagerThread.put(player, new PrideMusicManager((byte) 0, Minecraft.getInstance().getMusicManager()));
        loopTick(player);
    }

    public static void stopTick(Player player) {
        playerMusicManagerThread.remove(player);
    }

    public static void onTick(Player player) {
        if (playerMusicManagerThread.get(player) != null) {
            loopTick(player);
            if (player.tickCount % 10 == 0) {
                if (!Minecraft.getInstance().isPaused()) {
                    SheatProvider.provideSheat(player);
                    MusicCore.musicCore(player);
                    DynamicCam.dynamicCamTick(player);
                    AutoBattleMode.autoSwitch(EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class));
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
