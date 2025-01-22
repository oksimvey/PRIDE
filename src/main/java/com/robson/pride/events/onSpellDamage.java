package com.robson.pride.events;

import com.robson.pride.api.mechanics.MikiriCounter;
import com.robson.pride.api.utils.ProgressionUtils;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onSpellDamage {

    @SubscribeEvent
    public static void onSpellDmg(SpellDamageEvent event) {
        if (event.getEntity() != null && event.getSpellDamageSource().spell() != null) {
            Entity ent = event.getEntity();
            AbstractSpell spell = event.getSpellDamageSource().spell();
            if (event.getSpellDamageSource().getEntity() instanceof ServerPlayer player && event.getAmount() > 0) {
                ProgressionUtils.addXp(player, "Mind", (int) event.getAmount());
            }
            if (MikiriCounter.isDodgeCounterableSpell(event.getSpellDamageSource().getDirectEntity())) {
                if (ent.getPersistentData().getBoolean("mikiri_dodge")) {
                    MikiriCounter.onSpellMikiri(event, spell);
                }
            }
            if (MikiriCounter.isJumpCounterableSpell(event.getSpellDamageSource().spell())) {
                if (ent.getPersistentData().getBoolean("mikiri_sweep")) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
