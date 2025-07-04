package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public interface SunElement {

    ElementData DATA = new ElementData("Sun", ServerDataManager.SUN, ParticleRegistry.FIRE_PARTICLE.get(), ChatFormatting.GOLD, SoundRegistry.FIRE_BREATH_LOOP.get(),
            (byte) 20, SchoolRegister.SUN.get(), new ItemRenderingParams(new FixedRGB(252, 97, 0),
                    GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")))) {


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            ent.setSecondsOnFire((int) (amount / 3));
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

        public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
            if (ent != null && dmgent != null) {
                byte element = ElementalUtils.getElement(ent);
                float multiplier = 1;
                if (element == ServerDataManager.WATER || element == ServerDataManager.NATURE) {
                    multiplier = 0.5f;
                } else if (element == ServerDataManager.ICE || element == ServerDataManager.MOON) {
                    multiplier = 1.5f;
                }
                return MathUtils.getValueWithPercentageIncrease(multiplier *
                                MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:sun_resist")),
                        AttributeUtils.getAttributeValue(dmgent, "pride:sun_power"));
            }
            return amount;
        }
    };
}
