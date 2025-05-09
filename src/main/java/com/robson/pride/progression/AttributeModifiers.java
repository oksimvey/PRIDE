package com.robson.pride.progression;

import com.google.common.collect.Multimap;
import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.TagsUtils;
import com.robson.pride.registries.WeaponSkillRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.antlr.v4.runtime.misc.MultiMap;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.*;

import static com.robson.pride.api.client.CustomTooltips.findComponentArgument;

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
                    strmodifier = calculateWeaponAttributeModifier(player, item, tag,  "Strength");
                }
                if (tag.contains("requiredDexterity")){
                    modifiers = (byte) (modifiers + 1);
                    dexmodifier =  calculateWeaponAttributeModifier(player, item, tag,  "Dexterity");
                }
                if (tag.contains("requiredMind") || item.getOrCreateTag().contains("requiredMind")){
                    modifiers = (byte) (modifiers + 1);
                    mindmofier =  calculateWeaponAttributeModifier(player, item, tag,  "Mind");
                }
                float agroup = mindmofier + dexmodifier + strmodifier;
                float finalmodifier = agroup  / modifiers / 2;
                if (defaultmodifier * finalmodifier < -defaultmodifier * 0.95){
                    return -defaultmodifier * 0.95f;
                }
                return defaultmodifier * finalmodifier;
            }
        }
        return 0;
    }

    public static float calculateWeaponAttributeModifier(Player player, ItemStack item, CompoundTag tag, String attribute) {
        if (player != null && item != null && tag != null) {
            CompoundTag playertag =  TagsUtils.ClientPlayerTagsAcessor.playerTags.get(player);
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

    public static String getModifierBySlot(EquipmentSlot slot){
        return switch (slot){
            case FEET -> "eaa09522-0b9a-43cd-b199-e58ec94140b7";
            case LEGS -> "46bd5631-c8b6-4375-92cd-f7b77dcb5c29";
            case CHEST -> "932db49e-7acf-4d6d-9ad5-c1101da03617";
            default -> "afd5deb9-2ef3-4dd2-94b4-cb3744f75f64";
        };
    }

    public static int calculateArmorModifier(ItemStack item, CompoundTag tag, int defaultarmor){
       if (item != null && tag != null){
           if (tag.contains("attributes")){
               CompoundTag attributes = tag.getCompound("attributes");
               if (attributes.contains("armor")){
                   return attributes.getInt("armor") + defaultarmor;
               }
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

