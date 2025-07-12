package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.registries.AttributeRegister;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.List;

public interface MoonElement {
    ElementData DATA = new ElementData(ParticleTypes.DRAGON_BREATH, ChatFormatting.DARK_PURPLE, SoundRegistry.TELEKINESIS_LOOP.get(),
            (byte) 5, SchoolRegister.MOON.get(), new ItemRenderingParams(new FixedRGB((short) 134, (short) 0, (short) 237),
            GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png"))),
            AttributeRegister.MOON_POWER.get(), AttributeRegister.MOON_RESIST.get(),
            List.of(ElementDataManager.LIGHT, ElementDataManager.SUN), List.of(ElementDataManager.WATER, ElementDataManager.THUNDER)) {


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }
    };
}
