package com.robson.pride.events;

import com.robson.pride.api.client.CustomTooltips;
import com.robson.pride.api.data.PrideCapabilityReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ToolTipEvent {

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event){
        ItemStack item =  event.getItemStack();
        if (PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem()) != null) {
            CustomTooltips.deserializeWeaponTooltip(item, PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem()), event);
        }
        else if (PrideCapabilityReloadListener.CAPABILITY_ARMOR_DATA_MAP.get(item.getItem()) != null){
            CustomTooltips.deserializeArmorTooltip(item, PrideCapabilityReloadListener.CAPABILITY_ARMOR_DATA_MAP.get(item.getItem()), event);
        }
    }
}
