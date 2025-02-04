package com.robson.pride.effect;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class KillerAuraEffect extends MobEffect {

    public KillerAuraEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
