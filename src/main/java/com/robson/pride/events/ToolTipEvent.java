package com.robson.pride.events;

import com.robson.pride.api.client.CustomTooltips;
import com.robson.pride.api.data.manager.WeaponDataManager;
import com.robson.pride.api.data.types.item.WeaponData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ToolTipEvent {

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event) {
        ItemStack item = event.getItemStack();
        WeaponData data = WeaponDataManager.MANAGER.getByItem(item);
        if (data != null){
            CustomTooltips.deserializeWeaponTooltip(item, data, event);
        }
    }
}
