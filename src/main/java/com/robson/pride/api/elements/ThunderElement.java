package com.robson.pride.api.elements;

import com.robson.pride.api.client.GlintRenderTypes;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.SchoolRegister;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public interface ThunderElement {

    ElementData DATA = new ElementData("Thunder", ServerDataManager.THUNDER, ParticleRegistry.ELECTRICITY_PARTICLE.get(), ChatFormatting.AQUA, SoundRegistry.LIGHTNING_WOOSH_01.get(),
            (byte) 5, SchoolRegister.THUNDER.get(),  new ItemRenderingParams(new FixedRGB(0, 252, 227),
            GlintRenderTypes.createDirectGlint("direct_thunder", new ResourceLocation("pride:textures/glints/lightning_glint.png")),
            GlintRenderTypes.createDirectEntityGlint("direct_entity_thunder", new ResourceLocation("pride:textures/glints/lightning_glint.png")))) {


        public float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource) {
            this.playSound(ent, 1);
            thunderPassive(ent, dmgent, amount, new ArrayList<>(), true);
            return this.calculateFinalDamage(dmgent, ent, amount);
        }

        public void thunderPassive(Entity ent, Entity dmgent, float power, List<Entity> hitentities, boolean first) {
            if (ent != null && dmgent != null) {
                if (!dmgent.level().isClientSide) {
                    MagicManager.spawnParticles(dmgent.level(), ParticleHelper.ELECTRICITY, ent.getX(), ent.getY() + ent.getBbHeight() / 2, ent.getZ(), 10, ent.getBbWidth() / 3, ent.getBbHeight() / 3, ent.getBbWidth() / 3, 0.1, false);
                }
                PlaySoundUtils.playSound(ent, SoundRegistry.LIGHTNING_CAST.get(), 1, 1);
                if (!first) {
                    HealthUtils.hurtEntity(ent, this.calculateFinalDamage(dmgent, ent, power / 2), this.createDamageSource(dmgent));
                }
                AnimUtils.playAnim(ent, AnimationsRegister.ELECTROCUTATE, 0);
                hitentities.add(ent);
                if (!ElementalUtils.isNotInWater(ent, new Vec3(ent.getX(), ent.getY(), ent.getZ()))) {
                    chainThunder(ent, dmgent, power, hitentities);
                }
            }
        }

        public void chainThunder(Entity ent, Entity dmgent, float power, List<Entity> hitentities) {
            if (ent != null && dmgent != null) {
                AABB aabb = new AABB(ent.getX() - power, ent.getY() - power, ent.getZ() - power, ent.getX() + power, ent.getY() + power, ent.getZ() + power);
                List<Entity> listent = ent.level().getEntities(ent, aabb);
                for (Entity entko : listent) {
                    if (entko != null) {
                        if (!ElementalUtils.isNotInWater(entko, new Vec3(entko.getX(), entko.getY(), entko.getZ())) && SkillCore.canHit(dmgent, entko, hitentities)) {
                            double x1 = ent.getX();
                            double y1 = ent.getY() + ent.getBbHeight() / 2;
                            double z1 = ent.getZ();
                            double x2 = entko.getX();
                            double y2 = entko.getY() + entko.getBbHeight() / 2;
                            double z2 = entko.getZ();
                            PlaySoundUtils.playSound(ent, SoundRegistry.CHAIN_LIGHTNING_CHAIN.get(), 1, 1);
                            for (int i = 0; i < 100; i++) {
                                double t = i / (double) 50;
                                double finalx = x1 + (x2 - x1) * t;
                                double finaly = y1 + (y2 - y1) * t;
                                double finalz = z1 + (z2 - z1) * t;
                                ParticleUtils.spawnParticleOnServer(ParticleRegistry.ELECTRICITY_PARTICLE.get(), ent.level(), finalx, finaly, finalz, 1, 0, 0, 0, 0);
                                double distance = MathUtils.getTotalDistance((float) (x2 - finalx), (float) (y2 - finaly), (float) (z2 - finalz));
                                if (distance < 0.1) {
                                    thunderPassive(entko, dmgent, power, hitentities, false);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        public float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
            if (dmgent != null && ent != null) {
                byte element = ElementalUtils.getElement(ent);
                float multiplier = 1;
                if (element == ServerDataManager.NATURE || element == ServerDataManager.WIND) {
                    multiplier = 0.5f;
                } else if (element == ServerDataManager.WATER || element == ServerDataManager.ICE) {
                    multiplier = 1.5f;
                }
                return MathUtils.getValueWithPercentageIncrease(multiplier *
                                MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, "pride:thunder_resist")),
                        AttributeUtils.getAttributeValue(dmgent, "pride:thunder_power"));
            }
            return amount;
        }
    };
}
