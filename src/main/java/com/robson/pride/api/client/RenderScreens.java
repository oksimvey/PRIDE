package com.robson.pride.api.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface RenderScreens {

    ConcurrentMap<Player, Screen> PLAYER_SCREEN = new ConcurrentHashMap<>();

    static void renderPlayerScreens(Minecraft client) {
        if (client.player != null && PLAYER_SCREEN.get(client.player) != null && client.screen != PLAYER_SCREEN.get(client.player)) {
            client.setScreen(PLAYER_SCREEN.get(client.player));
        }
    }
}
