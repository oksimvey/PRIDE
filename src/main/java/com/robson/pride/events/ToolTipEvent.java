package com.robson.pride.events;

import com.robson.pride.api.client.CustomTooltips;
import com.robson.pride.api.data.manager.ServerDataManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ToolTipEvent {

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event) {
        ItemStack item = event.getItemStack();
        if (ServerDataManager.getWeaponData(item) != null) {
            CustomTooltips.deserializeWeaponTooltip(item, ServerDataManager.getWeaponData(item), event);
        }
    }
}
