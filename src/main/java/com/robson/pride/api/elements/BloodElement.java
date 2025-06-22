package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.particles.StringParticle;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;

import static com.robson.pride.api.skillcore.SkillCore.stackablePassiveBase;
import static com.robson.pride.api.utils.ElementalUtils.getElement;

public interface BloodElement {

    ElementBase DATA = new ElementBase("Blood", ParticleRegistry.BLOOD_PARTICLE.get(), ChatFormatting.DARK_RED,
            SoundRegistry.BLOOD_NEEDLE_IMPACT.get(), (byte) 5, SchoolRegister.BLOOD.get(), new ItemRenderingParams(new FixedRGB(255, 100, 50),
                    GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
                    GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")))) {


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            if (stackablePassiveBase(ent, this.calculateFinalDamage(dmgent, ent, amount), "bleed_stacks", StringParticle.StringParticleTypes.RED) && ent instanceof LivingEntity liv) {
                PlaySoundUtils.playSound(ent, EpicFightSounds.EVISCERATE.get(), 1, 1);
                ParticleUtils.spawnParticleRelativeToEntity(EpicFightParticles.BLOOD.get(), ent, 0, ent.getBbHeight() / 2, 0, 10, 0, 0, 0, 0.1);
                HealthUtils.hurtEntity(ent, liv.getMaxHealth() / 10, dmgent.damageSources().generic());
            }
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

        public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
            if (dmgent != null && ent != null) {
                byte element = getElement(ent);
                float multiplier = 1;
                if (element == ElementDataManager.LIGHT || element == ElementDataManager.WATER) {
                    multiplier = 0.5f;
                } else if (element == ElementDataManager.NATURE || element == ElementDataManager.THUNDER){
                    multiplier = 1.5f;
                }
                return MathUtils.getValueWithPercentageIncrease(multiplier *
                                MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:blood_resist")),
                        AttributeUtils.getAttributeValue(dmgent, "pride:blood_power"));
            }
            return amount;
        }
    };
}
