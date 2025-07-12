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

public interface NatureElement  {

    ElementData DATA = new ElementData(ParticleTypes.COMPOSTER, ChatFormatting.DARK_GREEN, SoundRegistry.POISON_SPLASH_BEGIN.get(),
            (byte) 5, SchoolRegister.NATURE.get(), new ItemRenderingParams(new FixedRGB((short) 13, (short) 145, (short) 22),
            GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png"))),
            AttributeRegister.NATURE_POWER.get(), AttributeRegister.NATURE_RESIST.get(),
            List.of(ElementDataManager.WIND, ElementDataManager.SUN), List.of(ElementDataManager.WATER, ElementDataManager.THUNDER)) {


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }
    };
}
