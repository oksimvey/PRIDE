package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.math.FixedRGB;
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

public interface WindElement {

    ElementBase DATA = new ElementBase() {

        public String getName(){
            return "Wind";
        }

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
            return new ItemRenderingParams(new FixedRGB(225, 227, 227),
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
                byte element = getElement(ent);
                float multiplier = 1;
                if (element == ElementDataManager.WATER || element == ElementDataManager.ICE) {
                    multiplier = 0.5f;
                } else if (element == ElementDataManager.SUN || element == ElementDataManager.NATURE) {
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
