package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.MathUtils;
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

public interface SunElement {

    ElementBase DATA = new ElementBase() {

        public String getName(){
            return "Sun";
        }

        public ParticleOptions getNormalParticleType() {
            return ParticleRegistry.FIRE_PARTICLE.get();
        }

        public ChatFormatting getChatColor() {
            return ChatFormatting.GOLD;
        }

        public SoundEvent getSound() {
            return SoundRegistry.FIRE_BREATH_LOOP.get();
        }

        public byte getParticleAmount() {
            return 20;
        }

        public ItemRenderingParams getItemRenderingParams() {
            return new ItemRenderingParams(new FixedRGB(252, 97, 0),
                    GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
                    GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")));

        }

        public SchoolType getSchool() {
            return SchoolRegister.SUN.get();
        }

        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            ent.setSecondsOnFire((int) (amount / 3));
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

        public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
            if (ent != null && dmgent != null) {
                byte element = getElement(ent);
                float multiplier = 1;
                if (element == ElementDataManager.WATER || element == ElementDataManager.NATURE) {
                    multiplier = 0.5f;
                } else if (element == ElementDataManager.ICE || element == ElementDataManager.MOON) {
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
