package com.robson.pride.api.utils;

import net.minecraft.world.item.ItemStack;

public class ElementalUtils {

    public static void applyDefaultElement(ItemStack item){
        if (item != null){
            if (TagCheckUtils.itemsTagCheck(item, "passives/darkness")){
                item.getTag().putString("passive_element", "Darkness");
            }
            if (TagCheckUtils.itemsTagCheck(item, "passives/light")){
                item.getTag().putString("passive_element", "Light");
            }
            if (TagCheckUtils.itemsTagCheck(item, "passives/thunder")){
                item.getTag().putString("passive_element", "Thunder");
            }
            if (TagCheckUtils.itemsTagCheck(item, "passives/sun")){
                item.getTag().putString("passive_element", "Sun");
            }
            if (TagCheckUtils.itemsTagCheck(item, "passives/moon")){
                item.getTag().putString("passive_element", "Moon");
            }
            if (TagCheckUtils.itemsTagCheck(item, "passives/blood")){
                item.getTag().putString("passive_element", "Blood");
            }
            if (TagCheckUtils.itemsTagCheck(item, "passives/nature")){
                item.getTag().putString("passive_element", "Nature");
            }
            if (TagCheckUtils.itemsTagCheck(item, "passives/wind")){
                item.getTag().putString("passive_element", "Wind");
            }
            if (TagCheckUtils.itemsTagCheck(item, "passives/ice")){
                item.getTag().putString("passive_element", "Ice");
            }
            if (TagCheckUtils.itemsTagCheck(item, "passives/blood")){
                item.getTag().putString("passive_element", "Water");
            }
        }
    }
}
