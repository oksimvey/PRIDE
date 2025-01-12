package com.robson.pride.api.skillcore;

import net.minecraft.ChatFormatting;
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
            return Component.literal(spellName.replace("_", " ") + " " + defaultName.getString() + "("  + stack.getTag().getString("rarity") + ")").withStyle(style -> style.withColor(color(stack.getTag().getString("rarity"))));
        }
        return defaultName;
    }

    public ChatFormatting color(String rarity){
        switch (rarity){
            case "Mythical"->{
                return ChatFormatting.DARK_RED;
            }
            case "Legendary" -> {
                return ChatFormatting.GOLD;
            }
            case "Epic" ->  {
                return ChatFormatting.DARK_PURPLE;
            }
            case "Rare" ->  {
                return ChatFormatting.BLUE;
            }
            case "Uncommon"-> {
                return ChatFormatting.GREEN;
            }
            case "Common"->{
                return ChatFormatting.GRAY;
            }
        }
        return ChatFormatting.GRAY;
    }
}