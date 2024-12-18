package com.robson.pride.effect;

import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.registries.AttributeRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Random;
import java.util.UUID;

public class WetEffect extends MobEffect {
    public static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("CE9DBC2A-EE3F-43F5-9DF7-F7F1EE4915A9");

    public WetEffect() {
        super(MobEffectCategory.HARMFUL, 0x56CBFD);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, WetEffect.MOVEMENT_SPEED_MODIFIER_UUID.toString(), -0.10, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(AttributeRegister.THUNDER_RESIST.get(), WetEffect.MOVEMENT_SPEED_MODIFIER_UUID.toString(), -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        Random random = new Random();
         if (random.nextInt(20) == 1){
             double radius = living.getBbWidth() / 4;
             ParticleUtils.spawnParticleRelativeToEntity(ParticleTypes.DRIPPING_WATER, living, -radius + Math.random(), (radius) + Math.random() * living.getBbHeight(), -radius + Math.random(), 1, 0, -0.5, 0, 0.25);
         }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
