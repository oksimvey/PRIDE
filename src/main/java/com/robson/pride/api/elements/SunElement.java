package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.registries.AttributeRegister;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.List;

public interface SunElement {

    ElementData DATA = new ElementData(ParticleRegistry.FIRE_PARTICLE.get(), ChatFormatting.GOLD, SoundRegistry.FIRE_BREATH_LOOP.get(),
            (byte) 20, SchoolRegister.SUN.get(), new ItemRenderingParams(new FixedRGB((short) 252, (short) 97, (short) 0),
                    GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png"))),
            AttributeRegister.SUN_POWER.get(), AttributeRegister.SUN_RESIST.get(),
            List.of(ElementDataManager.NATURE, ElementDataManager.WATER), List.of(ElementDataManager.MOON, ElementDataManager.ICE)) {


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            ent.setSecondsOnFire((int) (amount / 3));
            return this.calculateFinalDamage(dmgent, ent, amount);
        }
    };
}
