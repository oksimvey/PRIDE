package com.robson.pride.keybinding;

import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import static com.robson.pride.epicfight.styles.SheatProvider.unsheat;

public class onFPress {

    public static void swapHand(Player player) {
        if (player != null) {
            unsheat(player);
            addModifierToStyle(player);
            if (player.getMainHandItem().getTag() != null) {
                player.getMainHandItem().getTag().putBoolean("two_handed", !player.getMainHandItem().getTag().getBoolean("two_handed"));

            }
        }
    }

    public static void addModifierToStyle(LivingEntity living) {
        if (living != null) {
            if (ItemStackUtils.getStyle(living) == CapabilityItem.Styles.TWO_HAND) {
                AttributeUtils.addModifier(living, "minecraft:generic.attack_damage", "914e59d6-2cb8-4b1d-b3a7-76693f1eeb8d", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
            } else
                AttributeUtils.removeModifier(living, "minecraft:generic.attack_damage", "914e59d6-2cb8-4b1d-b3a7-76693f1eeb8d");
        }
    }
}
