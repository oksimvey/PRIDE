package com.robson.pride.events;

import com.robson.pride.api.npc.JsonDialoguesReader;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onRClickEnt {

    @SubscribeEvent
    public static void rClickEnt(PlayerInteractEvent.EntityInteractSpecific event){
        if (event.getEntity()  != null && event.getTarget() != null){
            if (JsonDialoguesReader.isSpeaking.get(event.getTarget()) == null) {
                JsonDialoguesReader.onInteraction(event.getTarget(), event.getEntity());
            }
        }
    }
}
