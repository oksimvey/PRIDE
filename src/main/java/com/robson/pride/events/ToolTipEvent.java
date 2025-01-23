package com.robson.pride.events;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.item.materials.ElementalGem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class ToolTipEvent {

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event){
        ItemStack item =  event.getItemStack();
        CompoundTag tag = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem());
        if (tag != null) {
            byte index = 5;
            if (tag.contains("attributes")) {
                CompoundTag attributes = tag.getCompound("attributes");
                for (String key : attributes.getAllKeys()) {
                    if (attributes.getCompound(key).contains("weight")) {
                        event.getToolTip().add(index, Component.literal(" " + attributes.getCompound(key).getDouble("weight") + " Weight"));
                    }
                }
            }
            if (item.getTag().contains("scaleMind")){
                event.getToolTip().add(index+1, Component.literal(" " + item.getTag().getString("scaleMind")  + " Mind Scale").withStyle(ChatFormatting.DARK_BLUE));
            }
            else if (tag.contains("scaleMind")){
                event.getToolTip().add(index+1, Component.literal(" " + tag.getString("scaleMind")  + " Mind Scale").withStyle(ChatFormatting.DARK_BLUE));
            }
            if (tag.contains("scaleDexterity")){
                event.getToolTip().add(index+1, Component.literal(" " + tag.getString("scaleDexterity")  + " Dexterity Scale").withStyle(ChatFormatting.DARK_GREEN));
            }
            if (tag.contains("scaleStrength")){
                event.getToolTip().add(index+1, Component.literal(" " + tag.getString("scaleStrength")  + " Strength Scale").withStyle(ChatFormatting.RED));
            }
            if (item.getTag().contains("requiredMind")){
                event.getToolTip().add(index + 1, Component.literal(" "  + item.getTag().getInt("requiredMind") + " Required Mind").withStyle(ChatFormatting.DARK_BLUE));
            }
            else if (tag.contains("requiredMind")){
                event.getToolTip().add(index+1, Component.literal(" " + tag.getInt("requiredMind")  + " Required Mind").withStyle(ChatFormatting.DARK_BLUE));
            }
            if (tag.contains("requiredDexterity")){
                event.getToolTip().add(index+1, Component.literal(" " + tag.getInt("requiredDexterity")  + " Required Dexterity").withStyle(ChatFormatting.DARK_GREEN));
            }
            if (tag.contains("requiredStrength")) {
                event.getToolTip().add(index + 1, Component.literal(" " + tag.getInt("requiredStrength") + " Required Strength").withStyle(ChatFormatting.RED));
            }
        }
    }
}
