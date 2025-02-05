package com.robson.pride.api.client;

import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.progression.AttributeModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class CustomTooltips {

    public static void deserializeWeaponTooltip(ItemStack item, CompoundTag tag, ItemTooltipEvent event){
        if (item != null && event != null && tag != null){
            byte index = 5;
            for (int i = 0; i < event.getToolTip().size(); i++) {
                Component line = event.getToolTip().get(i);
                if (findComponentArgument(line, Attributes.ATTACK_DAMAGE.getDescriptionId()) != null) {
                    float modifier = AttributeModifiers.calculateModifier(event.getEntity(), item, Float.parseFloat(line.getString().replace("Attack Damage", "")));
                    if (modifier != 0) {
                        MutableComponent name = Component.literal(line.getString() + " ").withStyle(ChatFormatting.WHITE);
                        MutableComponent modifiertext = Component.literal(AttributeModifiers.getSignal(modifier) + MathUtils.setDecimalsOnFloat(modifier, (byte) 2)).withStyle(AttributeModifiers.getModifierColor(item, modifier));
                        event.getToolTip().set(i,name.append(modifiertext));
                    }
                    break;
                }
            }
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

    public static void deserializeArmorTooltip(ItemStack item, CompoundTag tag, ItemTooltipEvent event){
        if (item != null && event != null && tag != null){
            for (int i = 0; i < event.getToolTip().size(); i++) {
                Component line = event.getToolTip().get(i);
                if (findComponentArgument(line, Attributes.ARMOR.getDescriptionId()) != null) {
                    float modifier = AttributeModifiers.calculateArmorModifier( item,tag, (int) Float.parseFloat(line.getString().replace("Armor", "").replace("+", "")));
                    if (modifier != 0) {
                          event.getToolTip().set(i, Component.literal( "+" + modifier + " Armor").withStyle(ChatFormatting.BLUE));
                    }
                    break;
                }
            }
        }
    }

    public static Object findComponentArgument(Component component, String key) {
        ComponentContents siblingComponent = component.getContents();
        if (siblingComponent instanceof TranslatableContents contents) {
            if (contents.getKey().equals(key)) {
                return component;
            }

            if (contents.getArgs() != null) {
                for(Object arg : contents.getArgs()) {
                    if (arg instanceof Component) {
                        Component argComponent = (Component)arg;
                        Object ret = findComponentArgument(argComponent, key);
                        if (ret != null) {
                            return ret;
                        }
                    }
                }
            }
        }

        for(Component siblingComponent1 : component.getSiblings()) {
            Object ret = findComponentArgument(siblingComponent1, key);
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }
}
