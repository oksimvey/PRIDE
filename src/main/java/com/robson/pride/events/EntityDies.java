package com.robson.pride.events;

import com.robson.pride.api.mechanics.ElementalPassives;
import com.robson.pride.registries.EffectRegister;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityDies {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void entityDiesEvent(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        if (living.hasEffect(EffectRegister.DIVINE_PROTECTION.get())) {
            event.setCanceled(true);
            ElementalPassives.divineResurrection(living);
        }
    }

    @SubscribeEvent
    public static void playerDeath(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            LivingEntity living = event.getEntity();
            if (living.hasEffect(EffectRegister.DIVINE_PROTECTION.get())) {
                event.setCanceled(true);
                ElementalPassives.divineResurrection(living);
            }
        }
    }
}
