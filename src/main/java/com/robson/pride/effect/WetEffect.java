package com.robson.pride.effect;

import com.robson.pride.api.utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class WetEffect extends MobEffect {
    public static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("CE9DBC2A-EE3F-43F5-9DF7-F7F1EE4915A9");

    public WetEffect() {
        super(MobEffectCategory.HARMFUL, 0x56CBFD);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, WetEffect.MOVEMENT_SPEED_MODIFIER_UUID.toString(), -0.10, AttributeModifier.Operation.MULTIPLY_TOTAL);
   }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
             double radius = living.getBbWidth() / 4;
             ParticleUtils.spawnParticleRelativeToEntity(ParticleTypes.FALLING_WATER, living, -radius + Math.random() * (radius + radius), (-living.getBbHeight() * 0.5) + Math.random() * ((living.getBbHeight())), -radius + Math.random() * (radius + radius), 1, 0, -0.5, 0, 0.25);
             living.clearFire();
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
