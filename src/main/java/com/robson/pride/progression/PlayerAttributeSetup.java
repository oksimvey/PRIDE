package com.robson.pride.progression;

import com.robson.pride.api.utils.AttributeUtils;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class PlayerAttributeSetup {

    public static void attributeSetup(Player player) {
        if (player != null) {
            player.getCapability(PlayerProgressionData.PLAYER_VARIABLES_CAPABILITY).ifPresent(playerData -> {
                double Strength = playerData.getStrengthLvl();
                double Dexterity = playerData.getDexterityLvl();
                double Vigor = playerData.getVigorLvl();
                double Endurance = playerData.getEnduranceLvl();
                double Mind = playerData.getMindLvl();
                AttributeUtils.addModifier(player, "minecraft:generic.attack_damage", "d7d184dc-2d1a-4b5b-9bb2-cd8133158c94", 1 + Strength /100, AttributeModifier.Operation.MULTIPLY_TOTAL);
                AttributeUtils.addModifier(player, "minecraft:attack_speed", "d7d184dc-2d1a-4b5b-9bb2-cd8133158c94", 1 + Dexterity / 200, AttributeModifier.Operation.MULTIPLY_TOTAL);
                AttributeUtils.addModifier(player, "minecraft:max_health", "d7d184dc-2d1a-4b5b-9bb2-cd8133158c94", 1.8 * Vigor, AttributeModifier.Operation.ADDITION);
                AttributeUtils.addModifier(player, "epicfight:staminar", "d7d184dc-2d1a-4b5b-9bb2-cd8133158c94", 0.38 * Endurance, AttributeModifier.Operation.ADDITION);
            });
        }
    }
}
