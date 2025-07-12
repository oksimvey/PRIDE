package com.robson.pride.events;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.types.skill.DurationSkillData;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onSpellDamage {

    @SubscribeEvent
    public static void onSpellDmg(SpellDamageEvent event) {
        if (event.getEntity() != null && event.getSpellDamageSource().spell() != null) {
            LivingEntity ent = event.getEntity();
            AbstractSpell spell = event.getSpellDamageSource().spell();
            for (byte skill : SkillDataManager.getActiveSkills(ent)){
                DurationSkillData data = SkillDataManager.INSTANCE.getByID(skill);
                if (data != null) {
                    data.onSpellDamage(ent, event);
                }
            }
            for (byte i = 1; true; i++){

            }
        }
    }
}
