package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;

import static com.robson.pride.api.utils.ElementalUtils.getElement;

public class IceElement extends ElementBase {

    public ParticleOptions getNormalParticleType() {
        return ParticleRegistry.SNOWFLAKE_PARTICLE.get();
    }

    public ChatFormatting getChatColor() {
        return ChatFormatting.DARK_AQUA;
    }

    public SoundEvent getSound() {
        return SoundRegistry.CONE_OF_COLD_LOOP.get();
    }

    public byte getParticleAmount() {
        return 5;
    }


    public ItemRenderingParams getItemRenderingParams() {
        return new ItemRenderingParams(50, 150, 250, new ResourceLocation("epicfight:textures/particle/efmc/fire_trail.png"),
                GlintRenderTypes.getFireGlintDirect(), GlintRenderTypes.getFreezeEntityGlintDirect());
    }


    public SchoolType getSchool() {
        return SchoolRegister.ICE.get();
    }

    public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
        this.playSound(ent, 1);
        return this.calculateFinalDamage(dmgent, ent, amount);
    }

    public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
        if (dmgent != null && ent != null) {
            String element = getElement(ent);
            float multiplier = 1;
            if (element.equals("Sun") || element.equals("Thunder")) {
                multiplier = 0.5f;
            } else if (element.equals("Water") || element.equals("Wind")) {
                multiplier = 1.5f;
            }
            return MathUtils.getValueWithPercentageIncrease(multiplier *
                            MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:ice_resist")),
                    AttributeUtils.getAttributeValue(dmgent, "pride:ice_power"));
        }
        return amount;
    }
}
