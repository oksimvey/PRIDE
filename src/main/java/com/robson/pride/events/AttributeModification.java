package com.robson.pride.events;

import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.ProgressionUtils;
import com.robson.pride.progression.AttributeModifiers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Arrays;
import java.util.List;

import static com.robson.pride.keybinding.KeySwapHand.addModifierToStyle;

@Mod.EventBusSubscriber
public class AttributeModification {

    private static List<EquipmentSlot> armorslots = Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

    @SubscribeEvent
    public static void onAttributeModification(LivingEquipmentChangeEvent event) {
        if (event.getEntity() != null) {
            if (event.getEntity() instanceof Player ent) {
                if (!armorslots.contains(event.getSlot())) {
                    AttributeUtils.addModifier(ent, EpicFightAttributes.WEIGHT.get(), "b4c793f6-b421-43cb-81e8-754fdfe278e4", ItemStackUtils.getWeaponWeight(ent.getOffhandItem()), AttributeModifier.Operation.ADDITION);
                    addModifierToStyle(ent);
                    float modifier = AttributeModifiers.calculateModifier(ent, ent.getMainHandItem(), 1);
                    if (modifier <= 0.1f) {
                        modifier = 0.1f;
                    }
                    if (!ProgressionUtils.haveReqs(ent)) {
                        AttributeUtils.addModifier(ent, Attributes.ATTACK_SPEED, "63104183-c72f-4f0b-9c98-b06743e886de", modifier / 2, AttributeModifier.Operation.MULTIPLY_TOTAL);
                        return;
                    }
                    if (modifier > 1) {
                        modifier = 1;
                    }
                    AttributeUtils.addModifier(ent, Attributes.ATTACK_SPEED, "63104183-c72f-4f0b-9c98-b06743e886de", modifier / 4, AttributeModifier.Operation.MULTIPLY_TOTAL);
                }
            }
        }
    }
}

