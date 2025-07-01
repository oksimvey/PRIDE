package com.robson.pride.events;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.mechanics.MikiriCounter;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onSpellDamage {

    @SubscribeEvent
    public static void onSpellDmg(SpellDamageEvent event) {
        if (event.getEntity() != null && event.getSpellDamageSource().spell() != null) {
            Entity ent = event.getEntity();
            AbstractSpell spell = event.getSpellDamageSource().spell();
            if (event.getSpellDamageSource().getEntity() instanceof Player player && event.getAmount() > 0) {

            }
            if (MikiriCounter.isDodgeCounterableSpell(event.getSpellDamageSource().getDirectEntity())) {
                if (MikiriCounter.canMobMikiri(ent, event.getSpellDamageSource().getEntity(), "Dodge")) {
                    MikiriCounter.onSpellMikiri(event, spell);
                }
                if (ent instanceof LivingEntity living) {

                }
            }
            if (MikiriCounter.isJumpCounterableSpell(event.getSpellDamageSource().spell())) {
                if (MikiriCounter.canMobMikiri(ent, event.getSpellDamageSource().getEntity(), "Jump")) {
                    event.setCanceled(true);
                }
            }
            for (byte i = 1; true; i++){
                ElementData data = ServerDataManager.getElementData(i);
                if (data == null){
                    return;
                }
                if (data.getSchool() == spell.getSchoolType()) {
                    event.setAmount(data.onHit(ent, event.getSpellDamageSource().getEntity(), event.getOriginalAmount(), true));
                }

            }
        }
    }
}
