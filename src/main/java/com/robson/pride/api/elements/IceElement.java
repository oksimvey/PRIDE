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

public interface IceElement {

    ElementData DATA = new ElementData(ParticleRegistry.SNOWFLAKE_PARTICLE.get(), ChatFormatting.DARK_AQUA,
            SoundRegistry.CONE_OF_COLD_LOOP.get(), (byte) 5, SchoolRegister.ICE.get(), new ItemRenderingParams(new FixedRGB((short) 50, (short) 100, (short) 250),
            GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png"))),
            AttributeRegister.ICE_POWER.get(), AttributeRegister.ICE_RESIST.get(),
            List.of(ElementDataManager.SUN, ElementDataManager.THUNDER), List.of(ElementDataManager.WATER, ElementDataManager.WIND)) {

        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

    };
}
