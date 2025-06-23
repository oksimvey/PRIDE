package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.DataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public interface WindElement {

    ElementData DATA = new ElementData("Wind", DataManager.WIND, ParticleTypes.CLOUD, ChatFormatting.WHITE, SoundRegistry.GUST_CAST.get(),
            (byte) 5, SchoolRegister.WIND.get(),new ItemRenderingParams(new FixedRGB(225, 227, 227),
            GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")))) {


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

        public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
            if (dmgent != null && ent != null) {
                byte element = ElementalUtils.getElement(ent);
                float multiplier = 1;
                if (element == DataManager.WATER || element == DataManager.ICE) {
                    multiplier = 0.5f;
                } else if (element == DataManager.SUN || element == DataManager.NATURE) {
                    multiplier = 1.5f;
                }
                return MathUtils.getValueWithPercentageIncrease(multiplier *
                                MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:wind_resist")),
                        AttributeUtils.getAttributeValue(dmgent, "pride:wind_power"));
            }
            return amount;
        }
    };
}
