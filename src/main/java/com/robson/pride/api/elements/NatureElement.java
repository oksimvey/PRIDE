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
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public interface NatureElement  {

    ElementData DATA = new ElementData("Nature", ServerDataManager.NATURE, ParticleTypes.COMPOSTER, ChatFormatting.DARK_GREEN, SoundRegistry.POISON_SPLASH_BEGIN.get(),
            (byte) 5, SchoolRegister.NATURE.get(), new ItemRenderingParams(new FixedRGB((short) 13, (short) 145, (short) 22),
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
                if (element == ServerDataManager.SUN || element == ServerDataManager.WIND) {
                    multiplier = 0.5f;
                } else if (element == ServerDataManager.THUNDER || element == ServerDataManager.WATER ){
                    multiplier = 1.5f;
                }
                return MathUtils.getValueWithPercentageIncrease(multiplier *
                                MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:nature_resist")),
                        AttributeUtils.getAttributeValue(dmgent, "pride:nature_power"));
            }
            return amount;
        }
    };
}
