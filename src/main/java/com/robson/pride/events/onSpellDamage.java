package com.robson.pride.events;

import com.robson.pride.api.elements.ElementBase;
import com.robson.pride.api.mechanics.ElementalPassives;
import com.robson.pride.api.mechanics.MikiriCounter;
import com.robson.pride.api.utils.ProgressionUtils;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.registries.ElementsRegister;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class onSpellDamage {

    @SubscribeEvent
    public static void onSpellDmg(SpellDamageEvent event) {
        if (event.getEntity() != null && event.getSpellDamageSource().spell() != null) {
            Entity ent = event.getEntity();
            AbstractSpell spell = event.getSpellDamageSource().spell();
            if (event.getSpellDamageSource().getEntity() instanceof Player player && event.getAmount() > 0) {
                ProgressionUtils.addXp(player, "Mind", (int) event.getAmount());
            }
            if (MikiriCounter.isDodgeCounterableSpell(event.getSpellDamageSource().getDirectEntity())) {
                if (MikiriCounter.canMobMikiri(ent, event.getSpellDamageSource().getEntity(), "Dodge")) {
                    MikiriCounter.onSpellMikiri(event, spell);
                }
                if(ent instanceof LivingEntity living){
                    if(living.hasEffect(EffectRegister.DARKNESS_WRATH.get())){
                        ElementalPassives.darknessSpellDmg(ent, event);
                    }
                }
            }
            if (MikiriCounter.isJumpCounterableSpell(event.getSpellDamageSource().spell())) {
                if (MikiriCounter.canMobMikiri(ent, event.getSpellDamageSource().getEntity(), "Jump")) {
                    event.setCanceled(true);
                }
            }
            for (ElementBase element : ElementsRegister.elements.values()){
                if (element.getSchool() == spell.getSchoolType()) {
                    event.setAmount(element.onHit(ent, event.getSpellDamageSource().getEntity(), event.getOriginalAmount(), true));
                }
            }
        }
    }
}
