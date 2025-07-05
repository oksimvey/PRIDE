package com.robson.pride.api.client;

import com.robson.pride.api.data.types.WeaponData;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.progression.AttributeModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class CustomTooltips {

    public static void hideGenericTooltips(ItemTooltipEvent event) {
        if (event == null) {
            return;
        }
        event.getToolTip().removeIf(line -> line.getString().contains("Hit") || line.getString().contains("Attack"));
    }

    public static void deserializeWeaponTooltip(ItemStack item, WeaponData data, ItemTooltipEvent event) {
        if (item != null && data != null && item.getTag() != null) {
            byte index = 5;
            for (int i = 0; i < event.getToolTip().size(); i++) {
                Component line = event.getToolTip().get(i);
                if (line.getString().contains("Damage")){
                    CapabilityItem itemcap = EpicFightCapabilities.getItemStackCapability(item);
                    if (itemcap != null) {
                        float originaldamage = (float) itemcap.getDamageAttributesInCondition(itemcap.getStyle(EpicFightCapabilities.getEntityPatch(Minecraft.getInstance().player, PlayerPatch.class))).get(Attributes.ATTACK_DAMAGE).getAmount();
                        float modifier = AttributeModifiers.calculateModifier(event.getEntity(), item, originaldamage);
                        MutableComponent name = Component.literal(" " + originaldamage + " Attack Damage ").withStyle(ChatFormatting.WHITE);
                        MutableComponent modifiertext = Component.literal( AttributeModifiers.getSignal(modifier)  + MathUtils.setDecimalsOnFloat(modifier, (byte) 2)).withStyle(AttributeModifiers.getModifierColor(item, modifier));
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
}
