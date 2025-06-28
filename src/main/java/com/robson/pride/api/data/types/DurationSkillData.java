package com.robson.pride.api.data.types;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.concurrent.ConcurrentHashMap;

public abstract class DurationSkillData {

    private ConcurrentHashMap<LivingEntity, Integer> activeTicksMap = new ConcurrentHashMap<>();

    public abstract void onStart(LivingEntity ent);

    public abstract void onAttacked(LivingEntity ent, LivingAttackEvent event);

    public abstract void onHurt(LivingEntity ent, LivingHurtEvent event);

    @OnlyIn(Dist.CLIENT)
    public  void onClientTick(LivingEntity ent){
        activeTicksMap.putIfAbsent(ent, 0);
        activeTicksMap.computeIfPresent(ent, (k, v) -> v + 1);
    }

    public void onEnd(LivingEntity ent){
        this.activeTicksMap.remove(ent);
    }

}
