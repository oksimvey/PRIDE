package com.robson.pride.api.mechanics;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.utils.*;
import com.robson.pride.particles.StringParticle;
import com.robson.pride.registries.AnimationsRegister;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.registries.WeaponSkillRegister;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.spells.nature.RootSpell;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;

public class ElementalPassives {


    public static void onElementalDamage(Entity ent, Entity dmgent, ItemStack item, LivingAttackEvent event) {
        if (ent != null && dmgent != null && item != null) {
            String element = item.getTag().getString("passive_element");
            if (!WeaponSkillRegister.elements.contains(element)){
                if (PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem()) != null){
                    CompoundTag tag  = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem());
                    if (tag.contains("element")){
                        element = tag.getString("element");
                    }
                }
            }
            switch (element) {
                case "Darkness" -> darknessPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:darkness_power")));

                case "Light" -> lightPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:light_power")));

                case "Thunder" -> thunderPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:thunder_power")), MathUtils.getRandomInt(999999999));

                case "Sun" -> sunPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:sun_power")));

                case "Moon" -> moonPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:moon_power")));

                case "Blood" -> bloodPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:blood_power")));

                case "Wind" -> windPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:wind_power")));

                case "Nature" -> naturePassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:nature_power")));

                case "Ice" -> icePassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:ice_power")));

                case "Water" -> waterPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:water_power")));
            }
        }
    }

    public static void darknessPassive(Entity ent, Entity dmgent, float power) {
        if (ent != null && dmgent != null) {
            if (dmgent instanceof Player p){
                ManaUtils.addMana(p, power);
            }
            if (ent instanceof LivingEntity living){
                ManaUtils.consumeMana(living, power);
                living.addEffect(new MobEffectInstance(MobEffects.WITHER, (int) power * 10, 0),living);
            }
        }
    }

    public static void darknessSpellDmg(Entity ent, SpellDamageEvent event){
        if (ent != null){
            event.setCanceled(true);
            Entity spellent = event.getSpellDamageSource().getDirectEntity();
            PlaySoundUtils.playSound(ent, SoundRegistry.TELEKINESIS_CAST.get(), 1,  1);
            if (spellent != null){
                Minecraft.getInstance().particleEngine.createParticle(WOMParticles.ANTITHEUS_BLACKHOLE_END.get(), spellent.getX(), spellent.getY(), spellent.getZ(), 0, 0, 0).scale(0.5f);
                spellent.remove(Entity.RemovalReason.DISCARDED);
            }
            else Minecraft.getInstance().particleEngine.createParticle(WOMParticles.ANTITHEUS_BLACKHOLE_END.get(), ent.getX(), ent.getY(), ent.getZ(), 0, 0, 0).scale(0.5f);
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

    public static void chainThunder(Entity ent, Entity dmgent, float power, int id) {
        if (ent != null && dmgent != null) {
            AABB aabb = new AABB(ent.getX() - power, ent.getY() - power, ent.getZ() - power, ent.getX() + power, ent.getY() + power, ent.getZ() + power);
            List<Entity> listent = ent.level().getEntities(ent, aabb);
            for (Entity entko : listent) {
                if (entko != null) {
                    if (!ElementalUtils.isNotInWater(entko, new Vec3(entko.getX(), entko.getY(), entko.getZ())) && SkillCore.canHit(dmgent, entko, "zap_id", id)) {
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

    public static void sunPassive(Entity ent, Entity dmgent, float power) {
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.FIRE_CAST.get(), 1, 1);
            HealthUtils.hurtEntity(ent, ElementalUtils.getFinalValueForSunDMG(ent, power), dmgent.damageSources().inFire());
            ent.setSecondsOnFire(3 + (int) (power / 10));
        }
    }

    public static void moonPassive(Entity ent, Entity dmgent, float power) {
        if (ent instanceof LivingEntity living && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.TELEKINESIS_LOOP.get(), 1, 1);
            int effectticks = (int) ElementalUtils.getFinalValueForMoonDMG(ent, power) * 20;
            living.addEffect(new MobEffectInstance(EffectRegister.HYPNOTIZED.get(), effectticks, 0), living);
        }
    }

    public static void bloodPassive(Entity ent, Entity dmgent, float power) {
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.BLOOD_EXPLOSION.get(), 1, 1);
            if (ent instanceof LivingEntity liv) {
                if (stackablePassiveBase(ent, ElementalUtils.getFinalValueForBloodDMG(ent, power), "bleed_stacks", StringParticle.StringParticleTypes.RED)) {
                    PlaySoundUtils.playSound(ent, EpicFightSounds.EVISCERATE.get(), 1, 1);
                    ParticleUtils.spawnParticleRelativeToEntity(EpicFightParticles.BLOOD.get(), ent, 0, ent.getBbHeight()/2, 0, 10, 0, 0, 0, 0.1);
                    HealthUtils.hurtEntity(ent, liv.getMaxHealth() / 10, dmgent.damageSources().generic());
                }
            }
        }
    }

    public static void windPassive(Entity ent, Entity dmgent, float power) {
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.GUST_CAST.get(), 1, 1);
        }
    }

    public static void naturePassive(Entity ent, Entity dmgent, float power) {
        if (ent != null && dmgent instanceof LivingEntity living) {
            PlaySoundUtils.playSound(ent, SoundRegistry.NATURE_CAST.get(), 1, 1);
            if (stackablePassiveBase(ent, ElementalUtils.getFinalValueForNatureDMG(ent, power), "root_stacks", StringParticle.StringParticleTypes.RED)) {
                SpellUtils.castSpell(living, new RootSpell(), 10, 0);
            }
        }
    }

    public static void icePassive(Entity ent, Entity dmgent, float power) {
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.RAY_OF_FROST.get(), 1, 1);
            byte multiplier = 1;
            if (!ElementalUtils.isNotInWater(ent, ent.position())) {
                multiplier = 2;
            }
            if (stackablePassiveBase(ent, multiplier * ElementalUtils.getFinalValueForIceDMG(ent, power), "frost_stacks", StringParticle.StringParticleTypes.LIGHT_BLUE)) {
                AnimUtils.applyStun(ent, StunType.SHORT, ElementalUtils.getFinalValueForIceDMG(ent, power) / 3);
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

    public static boolean stackablePassiveBase(Entity ent, float amounttoadd, String tag, StringParticle.StringParticleTypes type) {
        if (ent != null) {
            ent.getPersistentData().putByte(tag, (byte) (ent.getPersistentData().getByte(tag) + amounttoadd));
            if (ent.getPersistentData().getByte(tag) >= 100) {
                ParticleUtils.spawnNumberParticle(ent, 100 + "%", type, 60);
                ent.getPersistentData().putByte(tag, (byte) 0);
                ent.getPersistentData().putBoolean(tag, true);
                return true;
            }
            ParticleUtils.spawnNumberParticle(ent, ent.getPersistentData().getByte(tag) + "%", type, 60);
        }
        return false;
    }
}
