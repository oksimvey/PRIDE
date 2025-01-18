package com.robson.pride.api.utils;

import com.robson.pride.registries.EffectRegister;
import com.robson.pride.registries.ParticleRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class ElementalUtils {

    public static void setElement(Entity ent, String element) {
        if (ent instanceof Player player) {
            player.getPersistentData().putString("Element", element);
        }
    }

    public static ParticleOptions getParticleByElement(String element) {
        switch (element) {
            case "Darkness" -> {
                Random random = new Random();
                if (random.nextInt(20) == 1) {
                    return ParticleRegister.RED_LIGHTNING.get();
                } else return ParticleTypes.SMOKE;
            }
            case "Light" -> {
                return ParticleRegistry.WISP_PARTICLE.get();
            }
            case "Thunder" -> {
                return ParticleRegistry.ELECTRICITY_PARTICLE.get();
            }
            case "Sun" -> {
                return ParticleRegistry.FIRE_PARTICLE.get();
            }
            case "Moon" -> {
                return ParticleTypes.DRAGON_BREATH;
            }
            case "Blood" -> {
                return ParticleRegistry.BLOOD_PARTICLE.get();
            }
            case "Wind" -> {
                return ParticleTypes.CLOUD;
            }
            case "Nature" -> {
                return ParticleTypes.COMPOSTER;
            }
            case "Ice" -> {
                return ParticleRegistry.SNOWFLAKE_PARTICLE.get();
            }
            case "Water" -> {
                return new DustParticleOptions(new Vec3(0.3f, 0.5f, 1).normalize().toVector3f(), 1f);
            }
        }
        return null;
    }

    public static void rollElement(Entity ent){
        if (ent != null){
            short chance = (short) MathUtils.getRandomInt(1000);
            if (chance == 0){
                setElement(ent, "Darkness");
            }
            else if (chance >= 1 && chance <= 10){
                setElement(ent, "Light");
            }
            else if (chance >= 11 && chance <= 40){
                setElement(ent, "Thunder");
            }
            else if(chance >=41 && chance <= 90){
                setElement(ent, "Sun");
            }
            else if(chance >= 91 && chance <= 140){
                setElement(ent, "Moon");
            }
            else if(chance >= 141 && chance <= 240){
                setElement(ent, "Blood");
            }
            else if(chance >= 241 && chance <= 340){
                setElement(ent, "Wind");
            }
            else if(chance >= 341 && chance <= 560){
                setElement(ent, "Nature");
            }
            else if(chance >= 561 && chance <= 780){
                setElement(ent, "Ice");
            }
            else if (chance >= 781){
                setElement(ent, "Water");
            }
        }
    }

    public static String getElement(Entity ent) {
        if (ent != null) {
            if (ent instanceof Player player) {
                return player.getPersistentData().getString("Element");
            }
            else {
                if (TagCheckUtils.entityTagCheck(ent, "elements/darkness")){
                    return "Darkness";
                }
                else if (TagCheckUtils.entityTagCheck(ent, "elements/light")){
                    return "Light";
                }
                else if (TagCheckUtils.entityTagCheck(ent, "elements/thunder")){
                    return "Thunder";
                }
                else if (TagCheckUtils.entityTagCheck(ent, "elements/sun")){
                    return "Sun";
                }
                else if (TagCheckUtils.entityTagCheck(ent, "elements/moon")){
                    return "Moon";
                }
               else if (TagCheckUtils.entityTagCheck(ent, "elements/blood")){
                    return "Blood";
                }
                else if (TagCheckUtils.entityTagCheck(ent, "elements/wind")){
                    return "Wind";
                }
                else if (TagCheckUtils.entityTagCheck(ent, "elements/nature")){
                    return "Nature";
                }
                else if (TagCheckUtils.entityTagCheck(ent, "elements/ice")){
                    return "Ice";
                }
                else if (TagCheckUtils.entityTagCheck(ent, "elements/water")){
                    return "Water";
                }
            }
        }
        return "";
    }

    public static float getFinalValueForDarknessDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Moon") || element.equals("Blood")){
                multiplier = 0.5f;
            }
            else if (element.equals("Light") ||  element.equals("Sun")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:darkness_resist"));
        }
        return amount;
    }

    public static float getFinalValueForLightDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Darkness")){
                multiplier = 0.5f;
            }
            else if (element.equals("Moon") || element.equals("Blood")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:light_resist"));
        }
        return amount;
    }

    public static float getFinalValueForThunderDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Nature") || element.equals("Wind")){
                multiplier = 0.5f;
            }
            else if (element.equals("Water") || element.equals("Ice")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:thunder_resist"));
        }
        return amount;
    }

    public static float getFinalValueForSunDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Water") || element.equals("Nature")){
                multiplier = 0.5f;
            }
            else if (element.equals("Ice")|| element.equals("Moon")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:sun_resist"));
        }
        return amount;
    }

    public static float getFinalValueForMoonDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Light") || element.equals("Sun")){
                multiplier = 0.5f;
            }
            else if (element.equals("Thunder") || element.equals("Water")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:moon_resist"));
        }
        return amount;
    }

    public static float getFinalValueForBloodDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Light") || element.equals("Water")){
                multiplier = 0.5f;
            }
            else if (element.equals("Nature") || element.equals("Thunder")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:blood_resist"));
        }
        return amount;
    }

    public static float getFinalValueForWindDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Water") || element.equals("Ice")){
                multiplier = 0.5f;
            }
            else if (element.equals("Sun") || element.equals("Nature")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:wind_resist"));
        }
        return amount;
    }

    public static float getFinalValueForNatureDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Sun") || element.equals("Wind")){
                multiplier = 0.5f;
            }
            else if (element.equals("Thunder") || element.equals("Water")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:nature_resist"));
        }
        return amount;
    }

    public static float getFinalValueForIceDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Sun")|| element.equals("Thunder")){
                multiplier = 0.5f;
            }
            else if (element.equals("Water")|| element.equals("Wind")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:ice_resist"));
        }
        return amount;
    }

    public static float getFinalValueForWaterDMG(Entity ent, float amount){
        if  (ent != null){
            String element = getElement(ent);
            float multiplier  =  1;
            if (element.equals("Thunder") || element.equals("Nature")){
                multiplier = 0.5f;
            }
            else if (element.equals("Sun") || element.equals("Blood")){
                multiplier = 1.5f;
            }
            return multiplier * MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent,  "pride:water_resist"));
        }
        return amount;
    }

    public static boolean isNotInWater(Entity ent, Vec3 vec3) {
        if (ent instanceof LivingEntity living && vec3 != null) {
            BlockPos pos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
            return !ent.level().getBlockState(pos).is(Blocks.WATER) && !ent.level().isRainingAt(pos) && !living.hasEffect(EffectRegister.WET.get());
        }
        return false;
    }
}
