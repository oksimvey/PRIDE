package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.EffectRegister;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.spells.nature.RootSpell;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;

public class ElementalPassives {

    public static void onElementalDamage(Entity ent, Entity dmgent, ItemStack item, LivingAttackEvent event){
        if (ent != null && dmgent != null && item != null) {
            String element = item.getTag().getString("passive_element");
            if (Objects.equals(element, "Darkness") || TagCheckUtils.itemsTagCheck(item, "passives/darkness")) {
                darknessPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:darkness_power")));
            }
            if (Objects.equals(element, "Light") || TagCheckUtils.itemsTagCheck(item, "passives/light")) {
                lightPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:light_power")));
            }
            if (Objects.equals(element, "Thunder") || TagCheckUtils.itemsTagCheck(item, "passives/thunder")) {
                thunderPassive(ent, dmgent, MathUtils.getValueWithPercentageIncrease(event.getAmount(), AttributeUtils.getAttributeValue(dmgent, "pride:thunder_power")));
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

    public static void darknessPassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundEvents.WITHER_AMBIENT, 0.5f, 1);
        }
    }

    public static void lightPassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, EpicFightSounds.LASER_BLAST.get(), 1, 100);
        }
    }

    public static void thunderPassive(Entity ent, Entity dmgent,  float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.LIGHTNING_CAST.get(), 1, 1);
            HealthUtils.hurtEntity(ent, ElementalUtils.getFinalValueForThunderDMG(ent, power / 2), dmgent.damageSources().lightningBolt());
            AnimUtils.applyStun(ent, StunType.SHORT, ElementalUtils.getFinalValueForThunderDMG(ent, 2));
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
