package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;

import static com.robson.pride.api.utils.ElementalUtils.getElement;

public class WindElement extends ElementBase {

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

    public ItemRenderingParams getItemRenderingParams() {
        return new ItemRenderingParams(50, 150, 250, new ResourceLocation("epicfight:textures/particle/efmc/fire_trail.png"),
                GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
                GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")));

    }


    public SchoolType getSchool() {
        return SchoolRegister.WIND.get();
    }

    public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
        this.playSound(ent, 1);
        return this.calculateFinalDamage(dmgent, ent, amount);
    }

    public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
        if (dmgent != null && ent != null) {
            String element = getElement(ent);
            float multiplier = 1;
            if (element.equals("Water") || element.equals("Ice")) {
                multiplier = 0.5f;
            } else if (element.equals("Sun") || element.equals("Nature")) {
                multiplier = 1.5f;
            }
            return MathUtils.getValueWithPercentageIncrease(multiplier *
                            MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:wind_resist")),
                    AttributeUtils.getAttributeValue(dmgent, "pride:wind_power"));
        }
        return amount;
    }
}
