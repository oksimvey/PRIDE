package com.robson.pride.api.data.types;

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

    public abstract void onAttacked(LivingEntity ent, LivingAttackEvent event);

    public abstract void onHurt(LivingEntity ent, LivingHurtEvent event);

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
