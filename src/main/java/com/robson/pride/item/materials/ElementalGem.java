package com.robson.pride.item.materials;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ElementalGem extends Item {

    public ElementalGem(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public Component getName(ItemStack stack) {
        Component defaultName = super.getName(stack);
        return Component.literal(stack.getOrCreateTag().getString("passive_element")  + " Gem").withStyle(style -> style.withColor(getColorByElement(stack.getOrCreateTag().getString("passive_element"))));

    }
    public static ChatFormatting getColorByElement(String element) {
        switch (element) {
            case "Darkness" -> {
                return ChatFormatting.BLACK;
            }
            case "Light" -> {
                return ChatFormatting.YELLOW;
            }
            case "Thunder" -> {
                return ChatFormatting.AQUA;
            }
            case "Sun" -> {
                return ChatFormatting.GOLD;
            }
            case "Moon" -> {
                return ChatFormatting.DARK_PURPLE;
            }
            case "Blood" -> {
                return ChatFormatting.DARK_RED;
            }
            case "Wind" -> {
                return ChatFormatting.WHITE;
            }
            case "Nature" -> {
                return ChatFormatting.DARK_GREEN;
            }
            case "Ice" -> {
                return ChatFormatting.DARK_AQUA;
            }
            case "Water" -> {
                return ChatFormatting.DARK_BLUE;
            }
        }
        return ChatFormatting.GRAY;
    }
}
