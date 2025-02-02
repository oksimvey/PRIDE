package com.robson.pride.api.utils;

import com.robson.pride.api.ai.goals.JsonGoalsReader;
import com.robson.pride.api.ai.goals.PassiveSkillsReader;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.mechanics.MusicCore;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CustomTick {

    private static final ConcurrentHashMap<Player, Boolean> activePlayers = new ConcurrentHashMap<>();

    public static void startTick(Player player) {
        stopTick(player);
        activePlayers.put(player, true);
        loopTick(player);
    }

    public static void stopTick(Player player) {
        activePlayers.remove(player);
    }

    public static void onTick(Player player) {
        if (activePlayers.getOrDefault(player, false)) {
            loopTick(player);
           MusicCore.musicCore(player);
        }
    }


    public static void startRespawnTick(Player player) {
        stopTick(player);
        TimerUtil.schedule(() -> CustomTick.startTick(player), 502, TimeUnit.MILLISECONDS);
    }

    public static void loopTick(Player player) {
        TimerUtil.schedule(() -> {
            if (activePlayers.getOrDefault(player, false)) {
                onTick(player);
            }
        }, 500, TimeUnit.MILLISECONDS);
    }
}


