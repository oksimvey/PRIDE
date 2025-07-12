package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.particles.StringParticle;
import com.robson.pride.registries.AttributeRegister;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;

import java.util.List;

import static com.robson.pride.api.skillcore.SkillCore.stackablePassiveBase;

public interface BloodElement {

    ElementData DATA = new ElementData(ParticleRegistry.BLOOD_PARTICLE.get(), ChatFormatting.DARK_RED,
            SoundRegistry.BLOOD_NEEDLE_IMPACT.get(), (byte) 5, SchoolRegister.BLOOD.get(), new ItemRenderingParams(new FixedRGB((short) 255, (short) 100, (short) 50),
                    GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
                    GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png"))),
            AttributeRegister.BLOOD_POWER.get(), AttributeRegister.BLOOD_RESIST.get(),
            List.of(ElementDataManager.LIGHT, ElementDataManager.WATER), List.of(ElementDataManager.NATURE, ElementDataManager.THUNDER)) {


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            if (stackablePassiveBase(ent, this.calculateFinalDamage(dmgent, ent, amount), "bleed_stacks", StringParticle.StringParticleTypes.RED) && ent instanceof LivingEntity liv) {
                PlaySoundUtils.playSound(ent, EpicFightSounds.EVISCERATE.get(), 1, 1);
                ParticleUtils.spawnParticleRelativeToEntity(EpicFightParticles.BLOOD.get(), ent, 0, ent.getBbHeight() / 2, 0, 10, 0, 0, 0, 0.1);
                HealthUtils.hurtEntity(ent, liv.getMaxHealth() / 10, dmgent.damageSources().generic());
            }
            return this.calculateFinalDamage(dmgent, ent, amount);
        }
    };
}
