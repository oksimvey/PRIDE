package com.robson.pride.events;

import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.progression.AttributeModifiers;
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
            ItemStack leftitem = event.getLeft();
            ItemStack rightitem = event.getRight();
            if (rightitem.getItem() == ItemsRegister.WEAPON_ART.get()) {
                if (ElementalUtils.canPutWeaponArt(leftitem, rightitem)) {
                    ItemStack output = event.getLeft().copy();
                    output.getOrCreateTag().putShort("weapon_art", event.getRight().getTag().getShort("weapon_art"));
                    output.getOrCreateTag().putBoolean("hasweaponart", true);
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
            }
            if (!ParticleTracking.shouldRenderParticle(event.getLeft())) {
                if (rightitem.getItem() == ItemsRegister.ELEMENTAL_GEM.get()) {
                    if (ElementalUtils.canPutElementalPassive(leftitem, rightitem)) {
                        ItemStack output = event.getLeft().copy();
                        output.getOrCreateTag().putByte("passive_element", rightitem.getTag().getByte("passive_element"));
                        AttributeModifiers.setModifierForImbuement(output);
                        event.setOutput(output);
                        event.setCost(5);
                        event.setMaterialCost(1);
                    }
                }
            }
        }
    }
}
