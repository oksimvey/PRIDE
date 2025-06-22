package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.ParticleRegister;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;

import javax.swing.text.Element;
import java.util.Random;

import static com.robson.pride.api.utils.ElementalUtils.getElement;

public interface DarknessElement {

    ElementBase DATA = new ElementBase("Darkness", ParticleTypes.SMOKE, ChatFormatting.BLACK, SoundEvents.PARROT_IMITATE_WITHER, (byte) 3, SchoolRegister.DARKNESS.get(),
            new ItemRenderingParams(new FixedRGB(0, 0, 0),
                    GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
                    GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")))) {

        @Override
        public ParticleOptions getNormalParticleType() {
            Random random = new Random();
            if (random.nextInt(20) == 1) {
                return ParticleRegister.RED_LIGHTNING.get();
            }
            return super.getNormalParticleType();
        }


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

        public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
            if (dmgent != null && ent != null) {
                byte element = getElement(ent);
                float multiplier = 1;
                if (element == ElementDataManager.MOON || element == ElementDataManager.BLOOD) {
                    multiplier = 0.5f;
                } else if (element == ElementDataManager.LIGHT || element == ElementDataManager.SUN) {
                    multiplier = 1.5f;
                }
                return MathUtils.getValueWithPercentageIncrease(multiplier *
                                MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:darkness_resist")),
                        AttributeUtils.getAttributeValue(dmgent, "pride:darkness_power"));
            }
            return amount;
        }
    };
}
