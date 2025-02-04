package com.robson.pride.events;

import com.robson.pride.effect.DivineProtectionEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EffectEvents {

    @SubscribeEvent
    public static void onEffectStart(MobEffectEvent.Added event){
        if (event.getEffectInstance().getEffect() instanceof DivineProtectionEffect){
            if (event.getEntity() != null){
                if (event.getEntity() instanceof Player player){
                    player.getAbilities().mayfly = true;
                }
            }
        }
    }
    @SubscribeEvent
    public static void onEffectEnd(MobEffectEvent.Remove event){
        if (event.getEffect() instanceof DivineProtectionEffect){
            if (event.getEntity() != null){
                if (event.getEntity() instanceof Player player){
                    player.getAbilities().flying = false;
                    if (!player.isCreative()) {
                        player.getAbilities().mayfly = false;
                    }
                }
            }
        }
    }
}
