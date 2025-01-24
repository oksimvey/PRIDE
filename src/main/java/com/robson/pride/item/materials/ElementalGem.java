package com.robson.pride.item.materials;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.robson.pride.api.utils.ElementalUtils.getColorByElement;

public class ElementalGem extends Item {

    public ElementalGem(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public Component getName(ItemStack stack) {
        Component defaultName = super.getName(stack);
        return Component.literal(stack.getOrCreateTag().getString("passive_element")  + " Gem").withStyle(style -> style.withColor(getColorByElement(stack.getOrCreateTag().getString("passive_element"))));

    }
}
