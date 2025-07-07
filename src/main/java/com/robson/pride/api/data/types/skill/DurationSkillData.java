package com.robson.pride.api.data.types.skill;

import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.concurrent.ConcurrentHashMap;

public abstract class DurationSkillData {

    private ConcurrentHashMap<LivingEntity, Integer> tickwhenStarted = new ConcurrentHashMap<>();

    public void onStart(LivingEntity ent){
        this.tickwhenStarted.put(ent, ent.tickCount);
    }

    public void onHurtAnotherEntity(LivingEntity dmgent, LivingHurtEvent event){}

    public abstract void onAttacked(LivingEntity ent, LivingAttackEvent event);

    public void onSpellDamage(LivingEntity ent, SpellDamageEvent event){}

    public int getActiveTicks(LivingEntity ent){
        return ent.tickCount - this.tickwhenStarted.getOrDefault(ent, 0);
    }

    @OnlyIn(Dist.CLIENT)
    public void onClientTick(LivingEntity ent){
    }

    public void onEnd(LivingEntity ent){
        this.tickwhenStarted.remove(ent);
    }
}
