package com.robson.pride.events;

import com.robson.pride.api.data.item.ElementalGemData;
import com.robson.pride.api.data.item.ItemData;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.progression.AttributeModifiers;
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
            if (false) {
                if (ElementalUtils.canPutWeaponArt(leftitem, rightitem)) {
                    ItemStack output = event.getLeft().copy();
                    output.getOrCreateTag().putBoolean("hasweaponart", true);
                    event.setOutput(output);
                    event.setCost(5);
                    event.setMaterialCost(1);
                }
            }
            if (!ParticleTracking.shouldRenderParticle(event.getLeft())) {
                if (ItemData.getItemData(rightitem) instanceof ElementalGemData data) {
                    if (ElementalUtils.canPutElementalPassive(leftitem, rightitem)) {
                        ItemStack output = event.getLeft().copy();
                        output.getOrCreateTag().putByte("passive_element", data.getElement());
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
