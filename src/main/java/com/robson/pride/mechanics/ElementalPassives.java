package com.robson.pride.mechanics;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.EffectRegister;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;

public class ElementalPassives {

    public static void onElementalDamage(Entity ent, Entity dmgent, ItemStack item, LivingAttackEvent event){
        if (ent != null && dmgent != null && item != null) {
            String element = item.getTag().getString("passive_element");
            if (Objects.equals(element, "Darkness") || TagCheckUtils.itemsTagCheck(item, "passives/darkness")) {
                darknessPassive(ent, dmgent, 0);
            }
            if (Objects.equals(element, "Light") || TagCheckUtils.itemsTagCheck(item, "passives/light")) {
                lightPassive(ent, dmgent, 0);
            }
            if (Objects.equals(element, "Thunder") || TagCheckUtils.itemsTagCheck(item, "passives/thunder")) {
                thunderPassive(ent, dmgent, 0);
            }
            if (Objects.equals(element, "Sun") || TagCheckUtils.itemsTagCheck(item, "passives/sun")) {
                sunPassive(ent, dmgent, 0);
            }
            if (Objects.equals(element, "Moon") || TagCheckUtils.itemsTagCheck(item, "passives/moon")) {
                moonPassive(ent, dmgent, 0);
            }
            if (Objects.equals(element, "Blood") || TagCheckUtils.itemsTagCheck(item, "passives/blood")) {
                bloodPassive(ent, dmgent, 0);
            }
            if (Objects.equals(element, "Wind") || TagCheckUtils.itemsTagCheck(item, "passives/wind")) {
                windPassive(ent, dmgent, 0);
            }
            if (Objects.equals(element, "Nature") || TagCheckUtils.itemsTagCheck(item, "passives/nature")) {
                naturePassive(ent, dmgent, 0);
            }
            if (Objects.equals(element, "Ice") || TagCheckUtils.itemsTagCheck(item, "passives/ice")) {
                icePassive(ent, dmgent, 0);
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
            AnimUtils.applyStun(ent, StunType.SHORT, 1f);
        }
    }

    public static void sunPassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null){
            PlaySoundUtils.playSound(ent, SoundRegistry.FIRE_CAST.get(), 1, 1);
            ent.setSecondsOnFire(1);
        }
    }

    public static void moonPassive(Entity ent, Entity dmgent, float power){
        if (ent instanceof LivingEntity living && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.TELEKINESIS_LOOP.get(), 1, 1);
            if (!living.hasEffect(EffectRegister.HYPNOTIZED.get())) {
                living.addEffect(new MobEffectInstance(EffectRegister.HYPNOTIZED.get(), 400, 0), living);
            }
        }
    }

    public static void bloodPassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.BLOOD_EXPLOSION.get(), 1, 1);
        }
    }

    public static void windPassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.GUST_CAST.get(), 1, 1);
        }
    }

    public static void naturePassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.NATURE_CAST.get(), 1, 1);
        }
    }

    public static void icePassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundRegistry.RAY_OF_FROST.get(), 1, 1);
        }
    }

    public static void waterPassive(Entity ent, Entity dmgent, float power) {
        if (ent instanceof LivingEntity living && dmgent != null) {
            int effectticks = (int) MathUtils.getValueWithPercentageDecrease(power, AttributeUtils.getAttributeValue(ent, "pride:water_resist")) * 20;
            PlaySoundUtils.playSound(ent, SoundEvents.DROWNED_SWIM, 0.75f, 1);
            living.addEffect(new MobEffectInstance(EffectRegister.WET.get(), effectticks, 0), living);
        }
    }
}
