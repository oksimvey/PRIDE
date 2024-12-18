package com.robson.pride.mechanics;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.skills.magic.CloneSkill;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
               waterPassive(ent, dmgent, 1);
            }
        }
    }

    public static void darknessPassive(Entity ent, Entity dmgent, float power){
        if (ent != null && dmgent != null) {
            PlaySoundUtils.playSound(ent, SoundEvents.WITHER_AMBIENT, 1, 1);
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
            CloneSkill.summonPassiveClone(dmgent);
            if (!living.hasEffect(EffectRegister.HYPNOTIZED.get())) {
                living.addEffect(new MobEffectInstance(EffectRegister.HYPNOTIZED.get(), 400, 0), living);
                moonPassiveClone(ent, dmgent, power);
            }
        }
    }

    public static void moonPassiveClone(Entity ent, Entity dmgent, float power){
        if (ent instanceof LivingEntity livingEntity && dmgent != null){
            if (livingEntity.hasEffect(EffectRegister.HYPNOTIZED.get())){
                loopMoonPassive(livingEntity, dmgent, power);
            }
        }
    }

    public static void loopMoonPassive(LivingEntity ent, Entity dmgent, float power) {
        if (ent.hasEffect(EffectRegister.HYPNOTIZED.get())) {
            TimerUtil.schedule(() -> moonPassiveClone(ent, dmgent, power), 500, TimeUnit.MILLISECONDS);
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
            PlaySoundUtils.playSound(ent, SoundEvents.DROWNED_SWIM, 0.75f, 1);
            ent.clearFire();
            living.addEffect(new MobEffectInstance(EffectRegister.WET.get(), 400, 0), living);
        }
    }
}
