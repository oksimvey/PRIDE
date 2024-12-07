package com.robson.pride.events;

import com.robson.pride.api.utils.StaminaUtils;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerJoins{

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() != null){
            Entity ent = event.getEntity();
            StaminaUtils.resetStamina(ent);
            ent.getPersistentData().putBoolean("isParrying", false);
        }
    }
}
