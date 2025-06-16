package com.robson.pride.progression;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.TagsUtils;
import com.robson.pride.registries.WeaponSkillRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class AttributeModifiers {

    public static List<Character> scale_tiers = Arrays.asList('E', 'D', 'C', 'B', 'A', 'S');

    public static void setModifierForImbuement(ItemStack item) {
        if (item != null) {
            int newtier = getValueOfDefaultAttribute(item, "scale", "Mind") + 3;
            if (newtier > 5) {
                newtier = 5;
            }
            item.getOrCreateTag().putString("scaleMind", String.valueOf(scale_tiers.get(newtier)));
            item.getOrCreateTag().putInt("requiredMind", (int) ((getValueOfDefaultAttribute(item, "required", "Strength") + getValueOfDefaultAttribute(item, "required", "Dexterity")) / 1.5f) + getValueOfDefaultAttribute(item, "required", "Mind"));
        }
    }

    public static ChatFormatting getModifierColor(ItemStack item, float modifier) {
        if (item != null) {
            if (modifier < 0) {
                return ChatFormatting.RED;
            } else {
                String element = ElementalUtils.getItemElement(item);
                if (WeaponSkillRegister.elements.contains(element)) {
                    return ElementalUtils.getColorByElement(element);
                } else return ChatFormatting.DARK_GREEN;
            }
        }
        return ChatFormatting.WHITE;
    }

    public static int getValueOfDefaultAttribute(ItemStack item, String type, String stat) {
        if (item != null) {
            CompoundTag tag = null;
            if (tag != null) {
                if (tag.contains(type + stat)) {
                    if (type.equals("required")) {
                        return tag.getInt(type + stat);
                    }
                    else if (type.equals("scale")) {
                        return scale_tiers.indexOf(tag.getString(type + stat));
                    }
                }
            }
        }
        return 0;
    }

    public static String getSignal(float number) {
        if (number < 0) {
            return "";
        }
        return "+";
    }

    public static float calculateModifier(Player player, ItemStack item, float defaultmodifier) {
        if (item != null && player != null) {
            WeaponData data = WeaponData.getWeaponData(item);
            if (data != null) {
                float strmodifier = 0;
                float dexmodifier = 0;
                float mindmofier = 0;
                byte modifiers = 0;
                if (data.getAttributeReqs().requiredStrength() != 0) {
                    modifiers = (byte) (modifiers + 1);
                    strmodifier = calculateWeaponAttributeModifier(player, item, data.getAttributeReqs().strengthScale(), data.getAttributeReqs().requiredStrength(), "Strength");
                }
                if (data.getAttributeReqs().requiredDexterity() != 0) {
                    modifiers = (byte) (modifiers + 1);
                    dexmodifier = calculateWeaponAttributeModifier(player, item, data.getAttributeReqs().dexterityScale(), data.getAttributeReqs().requiredDexterity(), "Dexterity");
                }
                if (data.getAttributeReqs().requiredMind() != 0 || item.getOrCreateTag().contains("requiredMind")) {
                    modifiers = (byte) (modifiers + 1);
                    mindmofier = calculateWeaponAttributeModifier(player, item, data.getAttributeReqs().mindScale(), data.getAttributeReqs().requiredMind(), "Mind");
                }
                float agroup = mindmofier + dexmodifier + strmodifier;
                float finalmodifier = agroup / modifiers / 2;
                if (defaultmodifier * finalmodifier < -defaultmodifier * 0.95) {
                    return -defaultmodifier * 0.95f;
                }
                return defaultmodifier * finalmodifier;
            }
        }
        return 0;
    }

    public static float calculateWeaponAttributeModifier(Player player, ItemStack item, char scale, int required, String attribute) {
        if (player != null && item != null) {
            CompoundTag playertag = TagsUtils.playerTags.get(player);
            if (playertag != null) {
                int lvl = playertag.getInt(attribute + "Lvl");
                if (attribute.equals("Mind") && scale_tiers.contains(item.getOrCreateTag().getString("scaleMind")) && item.getOrCreateTag().contains("requiredMind")) {
                    required = item.getTag().getInt("requiredMind");
                    scale = item.getTag().getString("scaleMind").charAt(0);
                }
                float difference = lvl - required;
                return difference < 0 ? difference / 10 : difference * getIncrementByScale(scale) / 2;
            }
        }
        return 0;
    }

    public static String getModifierBySlot(EquipmentSlot slot) {
        return switch (slot) {
            case FEET -> "eaa09522-0b9a-43cd-b199-e58ec94140b7";
            case LEGS -> "46bd5631-c8b6-4375-92cd-f7b77dcb5c29";
            case CHEST -> "932db49e-7acf-4d6d-9ad5-c1101da03617";
            default -> "afd5deb9-2ef3-4dd2-94b4-cb3744f75f64";
        };
    }

    public static int calculateArmorModifier(ItemStack item, CompoundTag tag, int defaultarmor) {
        if (item != null && tag != null) {
            if (tag.contains("attributes")) {
                CompoundTag attributes = tag.getCompound("attributes");
                if (attributes.contains("armor")) {
                    return attributes.getInt("armor") + defaultarmor;
                }
            }
        }
        return 0;
    }

    public static float getIncrementByScale(char scale) {
        if (scale_tiers.contains(scale)) {
            return 0.01f + (float) scale_tiers.indexOf(scale) / 100;
        }
        return 0;
    }
}

