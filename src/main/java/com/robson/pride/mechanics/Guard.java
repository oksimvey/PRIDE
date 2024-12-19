package com.robson.pride.mechanics;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.robson.pride.api.utils.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.Objects;

public class Guard {
    public static void checkGuard(Entity ent, Entity ddmgent, LivingAttackEvent event){
        if (ent instanceof ServerPlayer player){
            if (player.isUsingItem()){
               checkParry(ent, ddmgent, event);
            }
            else {
                ProgressionUtils.addXp(player, "Vigor", (int) event.getAmount());
            }
        }
        else {
            AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
            if (livingEntityPatch != null) {
                if (livingEntityPatch.isBlocking()) {
                    checkParry(ent, ddmgent, event);
                }
            }
        }
        }
    public static void checkParry(Entity ent, Entity ddmgent, LivingAttackEvent event){
        if (ent instanceof ServerPlayer player){
            if (ent.getPersistentData().getBoolean("isParrying")){
                Parry.onParry(ent, ddmgent);
                ProgressionUtils.addXp(player, "Endurance", (int) event.getAmount() *  2);
            }
            else {
                onGuard(ent, ddmgent, event);
                ProgressionUtils.addXp(player, "Endurance", (int) event.getAmount());
            }
        }
        else {
            AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
            if (livingEntityPatch != null) {
                if (livingEntityPatch.getBlockTick() <= 5) {
                    Parry.onParry(ent, ddmgent);
                }
                else onGuard(ent, ddmgent,event);
            }
        }
    }

    public static void onGuard(Entity ent, Entity ddmgent, LivingAttackEvent event){
        String BlockType = ItemStackUtils.checkBlockType(ent);
        if (Objects.equals(BlockType, "weapon")){
            onMainHandWeaponGuard(ent, ddmgent, event);
        }
        if (Objects.equals(BlockType, "mainhandshield")){
            onMainHandShieldGuard(ent, ddmgent, event);
        }
        if (Objects.equals(BlockType, "offhandshield")){
            onOffHandShieldGuard(ent, ddmgent, event);
        }
    }

    public static void onOffHandShieldGuard(Entity ent, Entity ddmgent, LivingAttackEvent event){
        float weight = ItemStackUtils.getWeaponWeight(ent, InteractionHand.OFF_HAND, EquipmentSlot.OFFHAND);
        onGuardStamina(ent, ddmgent,event.getEntity(), weight * 2);
    }

    public static void onMainHandWeaponGuard(Entity ent, Entity ddmgent, LivingAttackEvent event){
        float weight = ItemStackUtils.getWeaponWeight(ent, InteractionHand.MAIN_HAND, EquipmentSlot.MAINHAND);
        onGuardStamina(ent, ddmgent,event.getEntity(), weight * 1.5f);
    }

    public static void onMainHandShieldGuard(Entity ent, Entity ddmgent, LivingAttackEvent event){
        float weight = ItemStackUtils.getWeaponWeight(ent, InteractionHand.MAIN_HAND, EquipmentSlot.MAINHAND);
        onGuardStamina(ent, ddmgent,event.getEntity(), weight * 2);
    }

    public static void onGuardStamina(Entity ent, Entity ddmgent, Entity dmgent, float weight){
        float impact = 1;
        if (ddmgent instanceof LivingEntity) {
            impact = AttributeUtils.getAttributeValue(ddmgent, "epicfight:impact");
        }
        if (weight > 100){
            weight = 100;
        }
        float amount = 0.5f + MathUtils.getValueWithPercentageDecrease(impact, weight);
        StaminaUtils.consumeStamina(ent, amount);
    }
}
