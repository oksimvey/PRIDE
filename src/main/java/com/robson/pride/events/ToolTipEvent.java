package com.robson.pride.events;

import com.robson.pride.api.client.CustomTooltips;
import com.robson.pride.api.data.item.WeaponData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ToolTipEvent {

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event) {
        ItemStack item = event.getItemStack();
        if (WeaponData.getWeaponData(item) != null) {
            CustomTooltips.deserializeWeaponTooltip(item, WeaponData.getWeaponData(item), event);
        }
    }
}
