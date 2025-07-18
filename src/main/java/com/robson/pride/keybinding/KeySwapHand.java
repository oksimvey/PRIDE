package com.robson.pride.keybinding;

import com.robson.pride.api.keybinding.BasicKey;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class KeySwapHand extends BasicKey{

        @Override
        public void onPress(Player player) {
            ItemStackUtils.swapHand(player);
        }


    public static void addModifierToStyle(LivingEntity living) {
        if (living != null) {
            if (ItemStackUtils.getStyle(living) == CapabilityItem.Styles.TWO_HAND) {
                AttributeUtils.addModifier(living, Attributes.ATTACK_DAMAGE, "914e59d6-2cb8-4b1d-b3a7-76693f1eeb8d", 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL);
            }
            else AttributeUtils.removeModifier(living, Attributes.ATTACK_DAMAGE, "914e59d6-2cb8-4b1d-b3a7-76693f1eeb8d");
        }
    }
}
