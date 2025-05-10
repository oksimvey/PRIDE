package com.robson.pride.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class PrideEffectBase extends MobEffect {

    protected PrideEffectBase(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public abstract void onEffectStart(LivingEntity ent);

    public abstract void onEffectEnd(LivingEntity ent);

    @OnlyIn(Dist.CLIENT)
    public abstract void prideClientTick(LivingEntity ent);

}
