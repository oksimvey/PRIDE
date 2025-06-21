package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;

import static com.robson.pride.api.utils.ElementalUtils.getElement;

public interface NatureElement  {

    ElementBase DATA = new ElementBase() {

        public String getName(){
            return "Nature";
        }

        public ParticleOptions getNormalParticleType() {
            return ParticleTypes.COMPOSTER;
        }

        public ChatFormatting getChatColor() {
            return ChatFormatting.DARK_GREEN;
        }

        public SoundEvent getSound() {
            return SoundRegistry.POISON_SPLASH_BEGIN.get();
        }

        public byte getParticleAmount() {
            return 5;
        }

        public ItemRenderingParams getItemRenderingParams() {
            return new ItemRenderingParams(new FixedRGB(13, 145, 22),
                    GlintRenderTypes.createDirectGlint("direct_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")),
                    GlintRenderTypes.createDirectEntityGlint("direct_entity_darkness", new ResourceLocation("pride:textures/glints/darkness_glint.png")));

        }


        public SchoolType getSchool() {
            return SchoolRegister.NATURE.get();
        }

        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

        public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
            if (dmgent != null && ent != null) {
                byte element = getElement(ent);
                float multiplier = 1;
                if (element == ElementDataManager.SUN || element == ElementDataManager.WIND) {
                    multiplier = 0.5f;
                } else if (element == ElementDataManager.THUNDER || element == ElementDataManager.WATER ){
                    multiplier = 1.5f;
                }
                return MathUtils.getValueWithPercentageIncrease(multiplier *
                                MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:nature_resist")),
                        AttributeUtils.getAttributeValue(dmgent, "pride:nature_power"));
            }
            return amount;
        }
    };
}
