package com.robson.pride.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public abstract class PrideEffectBase extends MobEffect {

    protected PrideEffectBase(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public abstract void pridetick(LivingEntity ent);
}
