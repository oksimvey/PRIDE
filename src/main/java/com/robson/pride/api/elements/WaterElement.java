package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import static com.robson.pride.api.utils.ElementalUtils.getElement;

public class WaterElement extends ElementBase {

    public ParticleOptions getNormalParticleType() {
        return new DustParticleOptions(new Vec3(0.3f, 0.5f, 1).normalize().toVector3f(), 1f);
    }

    public ChatFormatting getChatColor() {
        return ChatFormatting.DARK_BLUE;
    }

    public SoundEvent getSound() {
        return SoundEvents.DROWNED_SWIM;
    }

    public byte getParticleAmount() {
        return 5;
    }

    public ItemRenderingParams getItemRenderingParams() {
        return new ItemRenderingParams(50, 150, 250, new ResourceLocation("epicfight:textures/particle/efmc/fire_trail.png"),
                GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
                GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")));

    }


    public SchoolType getSchool() {
        return SchoolRegister.WATER.get();
    }

    public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
        this.playSound(ent, 1);
        return this.calculateFinalDamage(dmgent, ent, amount);
    }

    public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
        if (dmgent != null && ent != null) {
            String element = getElement(ent);
            float multiplier = 1;
            if (element.equals("Thunder") || element.equals("Nature")) {
                multiplier = 0.5f;
            } else if (element.equals("Sun") || element.equals("Blood")) {
                multiplier = 1.5f;
            }
            return MathUtils.getValueWithPercentageIncrease(multiplier *
                            MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:water_resist")),
                    AttributeUtils.getAttributeValue(dmgent, "pride:water_power"));
        }
        return amount;
    }
}
