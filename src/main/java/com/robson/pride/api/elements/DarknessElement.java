package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.registries.AttributeRegister;
import com.robson.pride.registries.ParticleRegister;
import com.robson.pride.registries.SchoolRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Random;

public interface DarknessElement {

    ElementData DATA = new ElementData(ParticleTypes.SMOKE, ChatFormatting.BLACK, SoundEvents.PARROT_IMITATE_WITHER, (byte) 3, SchoolRegister.DARKNESS.get(),
            new ItemRenderingParams(new FixedRGB((short) 0, (short) 0, (short) 0),
                    GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
                    GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png"))),
            AttributeRegister.DARKNESS_POWER.get(), AttributeRegister.DARKNESS_RESIST.get(),
            List.of(ElementDataManager.MOON, ElementDataManager.BLOOD), List.of(ElementDataManager.LIGHT, ElementDataManager.SUN)) {

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
    };
}
