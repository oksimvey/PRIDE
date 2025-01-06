package com.robson.pride.api.skillcore;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WeaponArtItem extends Item {

    public WeaponArtItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component defaultName = super.getName(stack);
        if (stack.hasTag() && stack.getTag().contains("weapon_art")) {
            String spellName = stack.getTag().getString("weapon_art");
            return Component.literal(spellName + " " + defaultName.getString());
        }

        return defaultName;
    }
}