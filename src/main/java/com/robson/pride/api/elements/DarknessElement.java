package com.robson.pride.api.elements;

import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.HealthUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.registries.ParticleRegister;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

import java.util.Random;

import static com.robson.pride.api.utils.ElementalUtils.getElement;

public class DarknessElement extends ElementBase {

    public ParticleOptions getNormalParticleType() {
        Random random = new Random();
        if (random.nextInt(20) == 1) {
            return ParticleRegister.RED_LIGHTNING.get();
        }
        return ParticleTypes.SMOKE;
    }

    public ChatFormatting getChatColor() {
        return ChatFormatting.BLACK;
    }

    public SoundEvent getSound() {
        return SoundEvents.PARROT_IMITATE_WITHER;
    }

    public byte getParticleAmount() {
        return 3;
    }


    public SchoolType getSchool(){
        return SchoolRegister.DARKNESS.get();
    }

    public void onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
        this.playSound(ent, 1);
        HealthUtils.hurtEntity(ent, this.calculateFinalDamage(ent, amount), this.createDamageSource(dmgent));
    }

    public float calculateFinalDamage(Entity ent, float amount) {
        if (ent != null) {
            String element = getElement(ent);
            float multiplier = 1;
            if (element.equals("Moon") || element.equals("Blood")) {
                multiplier = 0.5f;
            } else if (element.equals("Light") || element.equals("Sun")) {
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:darkness_resist"));
        }
        return amount;
    }

}
