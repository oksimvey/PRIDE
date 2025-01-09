package com.robson.pride.api.mechanics;

import com.robson.pride.api.skillcore.SkillBases;
import com.robson.pride.api.utils.*;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.EffectRegister;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.spells.nature.RootSpell;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.damagesource.StunType;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ElementalPassives {


    public static void onElementalDamage(Entity ent, Entity dmgent, ItemStack item, LivingAttackEvent event) {
        if (ent != null && dmgent != null && item != null) {
            String element = item.getTag().getString("passive_element");
            if (Objects.equals(element, "Darkness") || TagCheckUtils.itemsTagCheck(item, "passives/darkness")) {
                darknessPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:darkness_power")));
            }
            if (Objects.equals(element, "Light") || TagCheckUtils.itemsTagCheck(item, "passives/light")) {
                lightPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:light_power")));
            }
            if (Objects.equals(element, "Thunder") || TagCheckUtils.itemsTagCheck(item, "passives/thunder")) {
                thunderPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:thunder_power")), MathUtils.getRandomInt(999999999));
            }
            if (Objects.equals(element, "Sun") || TagCheckUtils.itemsTagCheck(item, "passives/sun")) {
                sunPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:sun_power")));
            }
            if (Objects.equals(element, "Moon") || TagCheckUtils.itemsTagCheck(item, "passives/moon")) {
                moonPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:moon_power")));
            }
            if (Objects.equals(element, "Blood") || TagCheckUtils.itemsTagCheck(item, "passives/blood")) {
                bloodPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:blood_power")));
            }
            if (Objects.equals(element, "Wind") || TagCheckUtils.itemsTagCheck(item, "passives/wind")) {
                windPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:wind_power")));
            }
            if (Objects.equals(element, "Nature") || TagCheckUtils.itemsTagCheck(item, "passives/nature")) {
                naturePassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:nature_power")));
            }
            if (Objects.equals(element, "Ice") || TagCheckUtils.itemsTagCheck(item, "passives/ice")) {
                icePassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:ice_power")));
            }
            if (Objects.equals(element, "Water") || TagCheckUtils.itemsTagCheck(item, "passives/water")) {
                waterPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:water_power")));
            }
        }
    }

    public static void darknessPassive(Entity ent, Entity dmgent, float power) {
        if (ent != null && dmgent != null) {
            for (int i = 0; i < ent.getBbHeight() * 25; i++) {
                Particle particle = Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.LARGE_SMOKE, ent.getX(), ent.getY() + ent.getBbHeight() / 2, ent.getZ(), 0.15, 0.15, 0.15);
                if (particle != null) {
                    particle.setLifetime(100);
                    ent.hurt(dmgent.damageSources().wither(), power * 2);
                }
            }
        }
    }

    public static void lightPassive(Entity ent, Entity dmgent, float power) {
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, EpicFightSounds.LASER_BLAST.get(), 1, 100);

        }
    }

    public static void thunderPassive(Entity ent, Entity dmgent, float power, int id) {
        if (ent != null && dmgent != null) {
            if (!dmgent.level().isClientSide) {
                MagicManager.spawnParticles(dmgent.level(), ParticleHelper.ELECTRICITY, ent.getX(), ent.getY() + ent.getBbHeight() / 2, ent.getZ(), 10, ent.getBbWidth() / 3, ent.getBbHeight() / 3, ent.getBbWidth() / 3, 0.1, false);
            }
            PlaySoundUtils.playSound(ent, SoundRegistry.LIGHTNING_CAST.get(), 1, 1);
            HealthUtils.hurtEntity(ent, ElementalUtils.getFinalValueForThunderDMG(ent, power / 2), dmgent.damageSources().lightningBolt());
            AnimUtils.playAnim(ent, AnimationsRegister.ELECTROCUTATE, 0);
            ent.getPersistentData().putInt("zap_id", id);
            if (!ElementalUtils.isNotInWater(ent, new Vec3(ent.getX(), ent.getY(), ent.getZ()))) {
                chainThunder(ent, dmgent, power, id);
            }
        }
    }

    public static void chainThunder(Entity ent, Entity dmgent, float power, int id){
        if (ent != null && dmgent != null) {
            AABB aabb = new AABB(ent.getX() - power, ent.getY() - power, ent.getZ() - power, ent.getX() + power, ent.getY() + power, ent.getZ() + power);
            List<Entity> listent = ent.level().getEntities(ent, aabb);
            for (Entity entko : listent) {
                if (entko != null) {
                    if (!ElementalUtils.isNotInWater(entko, new Vec3(entko.getX(), entko.getY(), entko.getZ())) && SkillBases.canHit(dmgent, entko, "zap_id", id) ) {
                        double x1 = ent.getX();
                        double y1 = ent.getY() + ent.getBbHeight() / 2;
                        double z1 = ent.getZ();
                        double x2 = entko.getX();
                        double y2 = entko.getY() + entko.getBbHeight() / 2;
                        double z2 = entko.getZ();
                        double finalx;
                        double finaly;
                        double finalz;
                        PlaySoundUtils.playSound(ent, SoundRegistry.CHAIN_LIGHTNING_CHAIN.get(), 1, 1);
                        for (int i = 0; i < 100; i++) {
                            double t = i / (double) 50;
                            finalx = x1 + (x2 - x1) * t;
                            finaly = y1 + (y2 - y1) * t;
                            finalz = z1 + (z2 - z1) * t;
                            ParticleUtils.spawnParticleOnServer(ParticleRegistry.ELECTRICITY_PARTICLE.get(), ent.level(), finalx, finaly, finalz, 1, 0, 0, 0, 0);
                            double distance = MathUtils.getTotalDistance(x2 - finalx, y2 - finaly, z2 - finalz);
                            if (distance < 0.1) {
                                thunderPassive(entko, dmgent, power, id);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void sunPassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.FIRE_CAST.get(), 1, 1);
            HealthUtils.hurtEntity(ent, ElementalUtils.getFinalValueForSunDMG(ent, power), dmgent.damageSources().inFire());
            ent.setSecondsOnFire(3 + (int) (power / 10));
        }
    }

    public static void moonPassive(Entity ent, Entity dmgent, float power){
        if (ent instanceof LivingEntity living && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.TELEKINESIS_LOOP.get(), 1, 1);
            int effectticks = (int) ElementalUtils.getFinalValueForMoonDMG(ent, power) * 20;
            living.addEffect(new MobEffectInstance(EffectRegister.HYPNOTIZED.get(), effectticks, 0), living);
        }
    }

    public static void bloodPassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.BLOOD_EXPLOSION.get(), 1, 1);
            if (stackablePassiveBase(ent, ElementalUtils.getFinalValueForBloodDMG(ent, power), "bleed_stacks")) {
                HealthUtils.hurtEntity(ent, AttributeUtils.getAttributeValue(ent, "minecraft:generic.max_health")/10, dmgent.damageSources().generic());
            }
        }
    }

    public static void windPassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.GUST_CAST.get(), 1, 1);
        }
    }

    public static void naturePassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent instanceof LivingEntity  living) {
            PlaySoundUtils.playSound(ent, SoundRegistry.NATURE_CAST.get(), 1, 1);
            if (stackablePassiveBase(ent, ElementalUtils.getFinalValueForNatureDMG(ent, power), "root_stacks")) {
                SpellUtils.castSpell(living, new RootSpell(), 10, 0);
            }
        }
    }

    public static void icePassive(Entity ent, Entity dmgent, float power) {
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.RAY_OF_FROST.get(), 1, 1);
            byte multiplier = 1;
            if (!ElementalUtils.isNotInWater(ent, new Vec3(ent.getX(), ent.getY(), ent.getZ()))) {
                multiplier = 2;
            }
            if (stackablePassiveBase(ent, multiplier * ElementalUtils.getFinalValueForIceDMG(ent, power), "frost_stacks")) {
                AnimUtils.applyStun(ent, StunType.HOLD, 15);
            }
        }
    }

    public static void waterPassive(Entity ent, Entity dmgent, float power) {
        if (ent instanceof LivingEntity living && dmgent != null) {
            int effectticks = (int) (ElementalUtils.getFinalValueForWaterDMG(ent, power) * 20);
            PlaySoundUtils.playSound(ent, SoundEvents.DROWNED_SWIM, 0.75f, 1);
            living.addEffect(new MobEffectInstance(EffectRegister.WET.get(), effectticks, 0), living);
        }
    }

    public static boolean stackablePassiveBase(Entity ent, float amounttoadd, String tag){
        if (ent != null){
                ent.getPersistentData().putByte(tag, (byte) (ent.getPersistentData().getByte(tag) + amounttoadd));
                if (ent.getPersistentData().getByte(tag) >= 100) {
                    ent.getPersistentData().putByte(tag, (byte) 0);
                    ent.getPersistentData().putBoolean(tag, true);
                    return true;
                }
            }
        return false;
    }
}
