package com.robson.pride.api.npc;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber
public class DialogueSubtitle {



    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent event) {

            event.getGuiGraphics().drawString(Minecraft.getInstance().font, Minecraft.getInstance().player.getPersistentData().getString("dialogue"), 25, 50, -1);

    }
}