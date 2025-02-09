package com.robson.pride.api.musiccore;

import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.musiccore.PrideMusicManager.playerMusicManagerThread;

public class MusicTick {

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
            MusicCore.musicCore(player);
        }
    }

    public static void startRespawnTick(Player player) {
        stopTick(player);
        TimerUtil.schedule(() -> startTick(player), 502, TimeUnit.MILLISECONDS);
    }

    public static void loopTick(Player player) {
        TimerUtil.schedule(() -> {
            if (playerMusicManagerThread.get(player) != null) {
                onTick(player);
            }
        }, 500, TimeUnit.MILLISECONDS);
    }
}
