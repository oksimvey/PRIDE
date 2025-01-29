package com.robson.pride.events;

import com.robson.pride.api.ai.dialogues.JsonInteractionsReader;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onRClickEnt {

    @SubscribeEvent
    public static void rClickEnt(PlayerInteractEvent.EntityInteractSpecific event){
        if (event.getEntity()  != null && event.getTarget() != null){
            if (JsonInteractionsReader.isSpeaking.get(event.getTarget()) == null) {
                JsonInteractionsReader.onInteraction(event.getTarget(), event.getEntity());
            }
        }
    }
}
