package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.item.ElementData;
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

public interface LightElement {

    ElementData DATA = new ElementData("Light", ServerDataManager.LIGHT, ParticleRegistry.WISP_PARTICLE.get(), ChatFormatting.YELLOW, SoundRegistry.CLOUD_OF_REGEN_LOOP.get(),
            (byte) 2, SchoolRegister.DARKNESS.get(),  new ItemRenderingParams(new FixedRGB(225, 225, 50),
            GlintRenderTypes.createDirectGlint("direct_light", new ResourceLocation("pride:textures/glints/light_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_light", new ResourceLocation("pride:textures/glints/light_glint.png")))) {
        

        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

        public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
            if (dmgent != null && ent != null) {
                byte element = ElementalUtils.getElement(ent);
                float multiplier = 1;
                if (element == ServerDataManager.DARKNESS) {
                    multiplier = 0.5f;
                } else if (element == ServerDataManager.MOON || element == ServerDataManager.BLOOD) {
                    multiplier = 1.5f;
                }
                return MathUtils.getValueWithPercentageIncrease(multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:light_resist")),
                        AttributeUtils.getAttributeValue(dmgent, "pride:light_power"));
            }
            return amount;
        }
    };
}