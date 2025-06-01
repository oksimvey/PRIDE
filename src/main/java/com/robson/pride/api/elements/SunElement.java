package com.robson.pride.api.elements;

import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.HealthUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

import static com.robson.pride.api.utils.ElementalUtils.getElement;

public class SunElement  extends ElementBase {

    public ParticleOptions getNormalParticleType() {
        return ParticleRegistry.FIRE_PARTICLE.get();
    }

    public ChatFormatting getChatColor() {
        return ChatFormatting.GOLD;
    }

    public SoundEvent getSound() {
        return SoundRegistry.FIRE_BREATH_LOOP.get();
    }

    public byte getParticleAmount() {
        return 5;
    }

    public SchoolType getSchool(){
        return SchoolRegister.SUN.get();
    }

    public void onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
        this.playSound(ent, 1);
        HealthUtils.hurtEntity(ent, this.calculateFinalDamage(ent, amount), this.createDamageSource(dmgent));
    }

    public float calculateFinalDamage(Entity ent, float amount) {
        if (ent != null) {
            String element = getElement(ent);
            float multiplier = 1;
            if (element.equals("Water") || element.equals("Nature")) {
                multiplier = 0.5f;
            } else if (element.equals("Ice") || element.equals("Moon")) {
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:sun_resist"));
        }
        return amount;
    }
}
