package com.robson.pride.progression;

import com.mojang.datafixers.util.Pair;
import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.MathUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AttributeModifiers {

    public static List<String> scale_tiers = Arrays.asList("E", "D", "C", "B", "A", "S");
    private static final UUID Strength_Modifier = UUID.fromString("725093a2-e7a7-437d-90e1-44730070d657");
    private static final UUID Dexterity_Modifier = UUID.fromString("f31fb58f-993c-46aa-a19c-f454b430f886");
    private static final UUID Mind_Modifier = UUID.fromString("976715de-9792-485d-b8f4-82273ee07f32");

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

    public static void addModifierToItem(Player player, ItemStack  item){
        if(item != null && player != null){
            CompoundTag tag = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(item.getItem());
            if (tag != null){
                if (tag.contains("requiredStrength")){
                    deserializeStrength(player, item, tag);
                }
                if (tag.contains("requiredStrength")){
                    deserializeStrength(player, item, tag);
                }
                if (tag.contains("requiredStrength")){
                    deserializeStrength(player, item, tag);
                }
            }
        }
    }

    public static void deserializeStrength(Player player, ItemStack item, CompoundTag tag){
        if (player != null && item != null && tag != null){
            int lvl = player.getPersistentData().getInt("StrengthLvl");
            double required = tag.getDouble("requiredStrength");
            float difference = (float) (lvl - required);
            float defaultmodifier = getDamageModifier(item, player);
            if (difference < 0){
            }
            else {
                String scale = tag.getString("scaleStrength");
                addDamageModifier(item, player, Strength_Modifier, (float) (100 * Math.pow(getIncrementByScale(scale), defaultmodifier)));
            }
        }
    }

    public static void addDamageModifier(ItemStack item, Player player, UUID uuid, float amount){
        if (item != null && player != null){
            CapabilityItem itemcap = EpicFightCapabilities.getItemStackCapability(item);
            if (itemcap != null) {

            }
        }
    }

    public static float getDamageModifier(ItemStack item, Player player){
        if (item != null && player != null){
            CapabilityItem itemcap = EpicFightCapabilities.getItemStackCapability(item);
            if (itemcap != null) {
                AttributeModifier modifier = itemcap.getDamageAttributesInCondition(ItemStackUtils.getStyle(player)).get(Attributes.ATTACK_DAMAGE);
                if (modifier != null) {
                    return (float) modifier.getAmount();
                }
            }
        }
        return 0;
    }

    public static float getIncrementByScale(String scale){
        if (scale_tiers.contains(scale)){
            return 1.01f + (float) scale_tiers.indexOf(scale) / 100;
        }
        return 1;
    }
}
