package com.robson.pride.events;

import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.keybinding.onFPress;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttributeModification {

    @SubscribeEvent
    public static void onAttributeModification(LivingEquipmentChangeEvent event){
        if (event.getEntity() != null){
            LivingEntity ent = event.getEntity();
            AttributeUtils.addModifier(ent, "epicfight:weight", "6a2101e2-db3a-4667-a13a-e392f422d2e9", ItemStackUtils.getWeaponWeight(ent, InteractionHand.MAIN_HAND, EquipmentSlot.MAINHAND), AttributeModifier.Operation.ADDITION);
            AttributeUtils.addModifier(ent, "epicfight:weight", "b4c793f6-b421-43cb-81e8-754fdfe278e4", ItemStackUtils.getWeaponWeight(ent, InteractionHand.OFF_HAND, EquipmentSlot.OFFHAND), AttributeModifier.Operation.ADDITION);
            onFPress.addModifierToStyle(ent);
        }
    }
}
