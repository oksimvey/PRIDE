package com.robson.pride.events;

import com.robson.pride.api.utils.ProgressionUtils;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onSpellDamage {

    @SubscribeEvent
    public static void onSpellDmg(SpellDamageEvent event){
        if (event.getSpellDamageSource().getEntity() instanceof ServerPlayer player && event.getAmount() > 0){
            ProgressionUtils.addXp(player, "Mind", (int) event.getAmount());
        }
    }
}
