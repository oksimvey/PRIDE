package com.robson.pride.events;

import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.mechanics.MikiriCounter;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onJump {

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event){
        if (event.getEntity() != null){
            if (event.getEntity() instanceof Player player) {
                if (StaminaUtils.getStamina(player) >= 3) {
                    StaminaUtils.consumeStamina(player, 3);
                    MikiriCounter.setMikiri(player, "Jump", 0, 350);
                }
            }
        }
    }
}
