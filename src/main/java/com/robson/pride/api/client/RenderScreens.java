package com.robson.pride.api.client;

import net.minecraft.client.Minecraft;

import static com.robson.pride.api.ai.dialogues.JsonInteractionsReader.isAnswering;

public class RenderScreens {

    public static void renderPlayerScreens(Minecraft client) {
        if (client.player != null) {
            if (isAnswering.get(client.player) != null) {
                client.setScreen(isAnswering.get(client.player));
            }
        }
    }
}
