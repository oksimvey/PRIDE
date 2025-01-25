package com.robson.pride.progression;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.registries.WeaponSkillRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class AttributeModifiers {

    public static List<String> scale_tiers = Arrays.asList("E", "D", "C", "B", "A", "S");

    public static void setModifierForImbuement(ItemStack item){
        if (item != null){
            int newtier = getValueOfDefaultAttribute(item, "scale", "Mind") + 3;
            if  (newtier > 5){
                newtier = 5;
            }
            item.getOrCreateTag().putString("scaleMind", scale_tiers.get(newtier));
            item.getOrCreateTag().putInt("requiredMind", (int) ((getValueOfDefaultAttribute(item, "required", "Strength") + getValueOfDefaultAttribute(item, "required", "Dexterity")) / 1.5f) + getValueOfDefaultAttribute(item,  "required", "Mind"));
        }
    }

    public static ChatFormatting getModifierColor(ItemStack item, float modifier){
        if (item  != null){
            if (modifier < 0){
                return ChatFormatting.RED;
            }
            else {
                String element = ElementalUtils.getItemElement(item);
                if (WeaponSkillRegister.elements.contains(element)){
                    return ElementalUtils.getColorByElement(element);
                }
                else return ChatFormatting.DARK_GREEN;
            }
        }
        return ChatFormatting.WHITE;
    }

    public static int getValueOfDefaultAttribute(ItemStack item, String type, String stat){
        if (item != null){
        CompoundTag tag = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem());
        if (tag != null) {
            if (tag.contains(type + stat)) {
                if (type.equals("required")) {
                    return tag.getInt(type + stat);
                } else if (type.equals("scale")) {
                    return scale_tiers.indexOf(tag.getString(type + stat));
                }
            }
        }
        }
        return 0;
    }

    public static String getSignal(float number){
        if (number < 0){
            return "";
        }
        return "+";
    }

    public static float calculateModifier(Player player, ItemStack  item, float defaultmodifier){
        if(item != null && player != null){
            CompoundTag tag = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem());
            if (tag != null){
                float strmodifier =  0;
                float dexmodifier = 0;
                float mindmofier = 0;
                byte modifiers = 0;
                if (tag.contains("requiredStrength")){
                    modifiers = (byte) (modifiers + 1);
                    strmodifier = calculateAttributeModifier(player, item, tag,  "Strength");
                }
                if (tag.contains("requiredDexterity")){
                    modifiers = (byte) (modifiers + 1);
                    dexmodifier =  calculateAttributeModifier(player, item, tag,  "Dexterity");
                }
                if (tag.contains("requiredMind") || item.getOrCreateTag().contains("requiredMind")){
                    modifiers = (byte) (modifiers + 1);
                    mindmofier =  calculateAttributeModifier(player, item, tag,  "Mind");
                }
                float agroup = mindmofier + dexmodifier + strmodifier;
                float finalmodifier = agroup  / modifiers;
                return defaultmodifier * finalmodifier;
            }
        }
        return 0;
    }

    public static float calculateAttributeModifier(Player player, ItemStack item, CompoundTag tag, String attribute) {
        if (player != null && item != null && tag != null) {
            CompoundTag playertag = ProgressionGUIRender.playertags.get(player);
            if (playertag != null) {
                int lvl = playertag.getInt(attribute + "Lvl");
                float required = (float) tag.getDouble("required" + attribute);
                String scale = tag.getString("scale" + attribute);
                if (attribute.equals("Mind") && scale_tiers.contains(item.getOrCreateTag().getString("scaleMind")) && item.getOrCreateTag().contains("requiredMind")) {
                    required = item.getTag().getInt("requiredMind");
                    scale = item.getTag().getString("scaleMind");
                }
                float difference = lvl - required;
                return difference < 0 ? difference / 10 : difference * getIncrementByScale(scale) / 2;
            }
        }
        return 0;
    }

    public static float getIncrementByScale(String scale){
        if (scale_tiers.contains(scale)){
            return 0.01f + (float) scale_tiers.indexOf(scale) / 100;
        }
        return 0;
    }
}

