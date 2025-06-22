package com.robson.pride.api.client;

import com.robson.pride.api.data.item.WeaponData;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.progression.AttributeModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class CustomTooltips {

    public static void deserializeWeaponTooltip(ItemStack item, WeaponData data, ItemTooltipEvent event) {
        if (item != null && event != null && data != null && item.getTag() != null) {
            byte index = 5;
            for (int i = 0; i < event.getToolTip().size(); i++) {
                Component line = event.getToolTip().get(i);
                if (line.getString().contains("Damage")){
                    float modifier = AttributeModifiers.calculateModifier(event.getEntity(), item, Float.parseFloat(line.getString().replace("Attack Damage", "")));
                    if (modifier != 0) {
                        MutableComponent name = Component.literal(line.getString() + " ").withStyle(ChatFormatting.WHITE);
                        MutableComponent modifiertext = Component.literal(AttributeModifiers.getSignal(modifier) + MathUtils.setDecimalsOnFloat(modifier, (byte) 2)).withStyle(AttributeModifiers.getModifierColor(item, modifier));
                        event.getToolTip().set(i, name.append(modifiertext));
                    }
                    break;
                }
            }
            if (data.getWeight() != 0) {
                        event.getToolTip().add(index, Component.literal(" " + data.getWeight() + " Weight"));
            }
            if (item.getTag().contains("scaleMind")) {
                event.getToolTip().add(index + 1, Component.literal(" " + item.getTag().getString("scaleMind") + " Mind Scale").withStyle(ChatFormatting.DARK_BLUE));
            } else if (data.getAttributeReqs().mindScale() != '\0') {
                event.getToolTip().add(index + 1, Component.literal(" " + data.getAttributeReqs().mindScale() + " Mind Scale").withStyle(ChatFormatting.DARK_BLUE));
            }
            if (data.getAttributeReqs().dexterityScale() != '\0') {
                event.getToolTip().add(index + 1, Component.literal(" " + data.getAttributeReqs().dexterityScale() + " Dexterity Scale").withStyle(ChatFormatting.DARK_GREEN));
            }
            if (data.getAttributeReqs().strengthScale() != '\0') {
                event.getToolTip().add(index + 1, Component.literal(" " + data.getAttributeReqs().strengthScale() + " Strength Scale").withStyle(ChatFormatting.RED));
            }
            if (item.getTag().contains("requiredMind")) {
                event.getToolTip().add(index + 1, Component.literal(" " + item.getTag().getInt("requiredMind") + " Required Mind").withStyle(ChatFormatting.DARK_BLUE));
            }
            else if (data.getAttributeReqs().requiredMind() != 0) {
                event.getToolTip().add(index + 1, Component.literal(" " + data.getAttributeReqs().requiredMind()+ " Required Mind").withStyle(ChatFormatting.DARK_BLUE));
            }
            if (data.getAttributeReqs().requiredDexterity() != 0) {
                event.getToolTip().add(index + 1, Component.literal(" " + data.getAttributeReqs().requiredDexterity() + " Required Dexterity").withStyle(ChatFormatting.DARK_GREEN));
            }
            if (data.getAttributeReqs().requiredStrength() != 0) {
                event.getToolTip().add(index + 1, Component.literal(" " + data.getAttributeReqs().requiredStrength() + " Required Strength").withStyle(ChatFormatting.RED));
            }
        }
    }

    private static Object findComponentArgument(Component component, String key) {
        ComponentContents var4 = component.getContents();
        if (var4 instanceof TranslatableContents contents) {
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

        for(Component siblingComponent : component.getSiblings()) {
            Object ret = findComponentArgument(siblingComponent, key);
            if (ret != null) {
                return ret;
            }
        }

        return null;
    }

}
