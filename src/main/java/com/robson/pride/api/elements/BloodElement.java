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
import yesman.epicfight.api.client.animation.property.TrailInfo;

import static com.robson.pride.api.utils.ElementalUtils.getElement;

public class BloodElement  extends ElementBase {

    public ParticleOptions getNormalParticleType() {
        return ParticleRegistry.BLOOD_PARTICLE.get();
    }

    public ChatFormatting getChatColor() {
        return ChatFormatting.DARK_RED;
    }

    public SoundEvent getSound() {
        return SoundRegistry.BLOOD_NEEDLE_IMPACT.get();
    }

    public byte getParticleAmount() {
        return 5;
    }

    public TrailInfo getTrailInfo(TrailInfo info){
        return info;
    }


    public SchoolType getSchool(){
        return SchoolRegister.BLOOD.get();
    }

    public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
        this.playSound(ent, 1);
        return this.calculateFinalDamage(dmgent, ent, amount);
    }

    public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
        if (dmgent != null && ent != null) {
            String element = getElement(ent);
            float multiplier = 1;
            if (element.equals("Light") || element.equals("Water")) {
                multiplier = 0.5f;
            } else if (element.equals("Nature") || element.equals("Thunder")) {
                multiplier = 1.5f;
            }
            return MathUtils.getValueWithPercentageIncrease(multiplier *
                            MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:blood_resist")),
                    AttributeUtils.getAttributeValue(dmgent, "pride:blood_power"));
        }
        return amount;
    }
}
