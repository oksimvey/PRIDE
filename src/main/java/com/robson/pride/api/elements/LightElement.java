package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.utils.AttributeUtils;
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

public interface LightElement {

    ElementBase DATA = new ElementBase() {

        public String getName(){
            return "Light";
        }

        public ItemRenderingParams getItemRenderingParams() {
            return new ItemRenderingParams(20, 20, 200, new ResourceLocation("pride:textures/particle/pointed_trail.png"),
                    GlintRenderTypes.createDirectGlint("direct_light", new ResourceLocation("pride:textures/glints/light_glint.png")),
                    GlintRenderTypes.createDirectEntityGlint("direct_entity_light", new ResourceLocation("pride:textures/glints/light_glint.png")));

        }


        public ParticleOptions getNormalParticleType() {
            return ParticleRegistry.WISP_PARTICLE.get();
        }

        public ChatFormatting getChatColor() {
            return ChatFormatting.YELLOW;
        }

        public SoundEvent getSound() {
            return SoundRegistry.CLOUD_OF_REGEN_LOOP.get();
        }

        public byte getParticleAmount() {
            return 2;
        }

        public SchoolType getSchool() {
            return SchoolRegister.LIGHT.get();
        }

        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

        public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
            if (dmgent != null && ent != null) {
                byte element = getElement(ent);
                float multiplier = 1;
                if (element == ElementDataManager.DARKNESS) {
                    multiplier = 0.5f;
                } else if (element == ElementDataManager.MOON || element == ElementDataManager.BLOOD) {
                    multiplier = 1.5f;
                }
                return MathUtils.getValueWithPercentageIncrease(multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:light_resist")),
                        AttributeUtils.getAttributeValue(dmgent, "pride:light_power"));
            }
            return amount;
        }
    };
}