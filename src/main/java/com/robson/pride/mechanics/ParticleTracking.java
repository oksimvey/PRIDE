package com.robson.pride.mechanics;

import com.robson.pride.api.utils.ElementalUtils;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class ParticleTracking {

    public static void auraImbuementTracking(LocalPlayer renderer){
    }

    public static boolean shouldRenderParticle(ItemStack item){
        if (item != null){
            String element = item.getTag().getString("passive_element");
            return Objects.equals(element, "Darkness") ||
                    Objects.equals(element, "Light") ||
                    Objects.equals(element, "Thunder") ||
                    Objects.equals(element, "Sun") ||
                    Objects.equals(element, "Moon") ||
                    Objects.equals(element, "Blood") ||
                    Objects.equals(element, "Wind") ||
                    Objects.equals(element, "Nature") ||
                    Objects.equals(element, "Ice") ||
                    Objects.equals(element, "Water");
        }
        return false;
    }

    public static ParticleOptions getParticle(String element){
        if (Objects.equals(element, "Darkness")) {
            return ParticleTypes.SMOKE;
        }
        if (Objects.equals(element, "Light")) {
           return ParticleRegistry.WISP_PARTICLE.get();
        }
        if (Objects.equals(element, "Thunder")) {
            return ParticleRegistry.ELECTRICITY_PARTICLE.get();
        }
        if (Objects.equals(element, "Sun")) {
            return ParticleRegistry.FIRE_PARTICLE.get();
        }
        if (Objects.equals(element, "Moon")) {
            return ParticleTypes.DRAGON_BREATH;
        }
        if (Objects.equals(element, "Blood")) {
            return ParticleRegistry.BLOOD_PARTICLE.get();
        }
        if (Objects.equals(element, "Wind")) {
            return ParticleTypes.CLOUD;
        }
        if (Objects.equals(element, "Nature")) {
            return ParticleTypes.COMPOSTER;
        }
        if (Objects.equals(element, "Ice")) {
            return ParticleRegistry.SNOWFLAKE_PARTICLE.get();
        }
        if (Objects.equals(element, "Water")) {
           return ParticleTypes.DOLPHIN;
        }
        return null;
    }
}
