package com.robson.pride.api.elements;

import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.HealthUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

import static com.robson.pride.api.utils.ElementalUtils.getElement;

public class WindElement  extends ElementBase {

    public ParticleOptions getNormalParticleType() {
        return ParticleTypes.CLOUD;
    }

    public ChatFormatting getChatColor() {
        return ChatFormatting.WHITE;
    }

    public SoundEvent getSound() {
        return SoundRegistry.GUST_CHARGE.get();
    }

    public byte getParticleAmount() {
        return 5;
    }


    public SchoolType getSchool(){
        return SchoolRegister.WIND.get();
    }

    public void onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
        this.playSound(ent, 1);
        HealthUtils.hurtEntity(ent, this.calculateFinalDamage(ent, amount), this.createDamageSource(dmgent));
    }

    public float calculateFinalDamage(Entity ent, float amount) {
        if (ent != null) {
            String element = getElement(ent);
            float multiplier = 1;
            if (element.equals("Water") || element.equals("Ice")) {
                multiplier = 0.5f;
            } else if (element.equals("Sun") || element.equals("Nature")) {
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:wind_resist"));
        }
        return amount;
    }
}
