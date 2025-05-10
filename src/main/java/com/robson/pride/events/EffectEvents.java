package com.robson.pride.events;

import com.robson.pride.effect.DivineProtectionEffect;
import com.robson.pride.effect.PrideEffectBase;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EffectEvents {

    @SubscribeEvent
    public static void onEffectStart(MobEffectEvent.Added event){
        if (event.getEntity() != null && event.getEffectInstance().getEffect() instanceof PrideEffectBase prideEffectBase){
            prideEffectBase.onEffectStart(event.getEntity());
        }
    }
    @SubscribeEvent
    public static void onEffectEnd(MobEffectEvent.Remove event){
        if (event.getEntity() != null && event.getEffectInstance().getEffect() instanceof PrideEffectBase prideEffectBase){
            prideEffectBase.onEffectEnd(event.getEntity());
        }
    }
}
