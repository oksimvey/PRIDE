package com.robson.pride.api.data.types;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public abstract class DurationSkillData {

    int activeTicks = 0;

    public abstract void onStart(LivingEntity ent);

    public abstract void onAttacked(LivingEntity ent, LivingAttackEvent event);

    public abstract void onHurt(LivingEntity ent, LivingHurtEvent event);

    @OnlyIn(Dist.CLIENT)
    public abstract void onClientTick(LivingEntity ent);

    public abstract void onEnd(LivingEntity ent);

}
