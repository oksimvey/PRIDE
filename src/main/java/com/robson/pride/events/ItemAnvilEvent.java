package com.robson.pride.events;

import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.registries.ItemsRegister;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemAnvilEvent {

    @SubscribeEvent
    public static void anvilEvent(AnvilUpdateEvent event) {
        if (event != null) {
            if (event.getRight().getItem() == ItemsRegister.WEAPON_ART.get()) {
                ItemStack output = event.getLeft().copy();
                output.getOrCreateTag().putString("weapon_art", event.getRight().getTag().getString("weapon_art"));
                output.getOrCreateTag().putBoolean("hasweaponart", true);
                event.setOutput(output);
                event.setCost(5);
                event.setMaterialCost(1);
            }
            if (!ParticleTracking.shouldRenderParticle(event.getLeft(), event.getPlayer())) {
                ItemStack output = event.getLeft().copy();
                if (event.getRight().getItem() == ItemsRegister.DARKNESS_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Darkness");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
                if (event.getRight().getItem() == ItemsRegister.LIGHT_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Light");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
                if (event.getRight().getItem() == ItemsRegister.THUNDER_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Thunder");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
                if (event.getRight().getItem() == ItemsRegister.SUN_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Sun");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
                if (event.getRight().getItem() == ItemsRegister.MOON_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Moon");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
                if (event.getRight().getItem() == ItemsRegister.BLOOD_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Blood");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
                if (event.getRight().getItem() == ItemsRegister.WIND_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Wind");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
                if (event.getRight().getItem() == ItemsRegister.NATURE_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Nature");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
                if (event.getRight().getItem() == ItemsRegister.ICE_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Ice");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
                if (event.getRight().getItem() == ItemsRegister.WATER_GEM.get()) {
                    output.getOrCreateTag().putString("passive_element", "Water");
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
            }
        }
    }
}
