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

public interface LightElement {

    ElementData DATA = new ElementData(ParticleRegistry.WISP_PARTICLE.get(), ChatFormatting.YELLOW, SoundRegistry.CLOUD_OF_REGEN_LOOP.get(),
            (byte) 2, SchoolRegister.DARKNESS.get(),  new ItemRenderingParams(new FixedRGB((short) 225, (short) 225, (short) 50),
            GlintRenderTypes.createDirectGlint("direct_light", new ResourceLocation("pride:textures/glints/light_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_light", new ResourceLocation("pride:textures/glints/light_glint.png"))),
            AttributeRegister.LIGHT_POWER.get(), AttributeRegister.LIGHT_RESIST.get(),
            List.of(ElementDataManager.DARKNESS), List.of(ElementDataManager.MOON, ElementDataManager.BLOOD)) {
        

        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

    };
}