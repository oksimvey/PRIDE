package com.robson.pride.events;

import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.keybinding.onFPress;
import com.robson.pride.progression.AttributeModifiers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttributeModification {

    @SubscribeEvent
    public static void onAttributeModification(LivingEquipmentChangeEvent event) {
        if (event.getEntity() != null) {
            if (event.getEntity() instanceof Player ent) {
                AttributeUtils.addModifier(ent, "epicfight:weight", "b4c793f6-b421-43cb-81e8-754fdfe278e4", ItemStackUtils.getWeaponWeight(ent, InteractionHand.OFF_HAND, EquipmentSlot.OFFHAND), AttributeModifier.Operation.ADDITION);
                onFPress.addModifierToStyle(ent);
                AttributeModifiers.addModifierToItem(ent, ent.getMainHandItem());
                AttributeModifiers.addModifierToItem(ent, ent.getOffhandItem());
            }
        }
    }
}
