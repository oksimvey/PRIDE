package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.registries.AttributeRegister;
import com.robson.pride.registries.SchoolRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface WaterElement {

    ElementData DATA = new ElementData(new DustParticleOptions(new Vec3(0.3f, 0.5f, 1).normalize().toVector3f(), 1f),
            ChatFormatting.DARK_BLUE, SoundEvents.DROWNED_SWIM, (byte) 5, SchoolRegister.WATER.get(), new ItemRenderingParams(new FixedRGB((short) 9, (short) 96, (short) 184),
            GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png"))),
            AttributeRegister.WATER_POWER.get(), AttributeRegister.WATER_RESIST.get(),
            List.of(ElementDataManager.THUNDER, ElementDataManager.NATURE), List.of(ElementDataManager.SUN, ElementDataManager.BLOOD)){


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }
    };
}
