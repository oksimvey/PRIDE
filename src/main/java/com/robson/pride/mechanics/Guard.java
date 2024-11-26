package com.robson.pride.mechanics;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ProgressionUtils;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.api.utils.TagCheckUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
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
        String BlockType = TagCheckUtils.checkBlockType((LivingEntity) ent);
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
        float weight = AttributeUtils.findAttributeModifierbyUUID((LivingEntity) ent, "88440558-2872-48a2-843c-9eda17a7aad0", "epicfight:weight");
        onGuardStamina(ent, ddmgent, weight * 2);
    }

    public static void onMainHandWeaponGuard(Entity ent, Entity ddmgent, LivingAttackEvent event){
        float weight = AttributeUtils.findAttributeModifierbyUUID((LivingEntity) ent, "a516026a-bee2-4014-bcb6-b6a5775553de", "epicfight:weight");
        onGuardStamina(ent, ddmgent, weight * 1.5f);
    }

    public static void onMainHandShieldGuard(Entity ent, Entity ddmgent, LivingAttackEvent event){
        float weight = AttributeUtils.findAttributeModifierbyUUID((LivingEntity) ent, "a516026a-bee2-4014-bcb6-b6a5775553de", "epicfight:weight");
        onGuardStamina(ent, ddmgent, weight * 2);
    }

    public static void onGuardStamina(Entity ent, Entity ddmgent, float weight){
        float impact = AttributeUtils.getAttributeValue((LivingEntity) ddmgent, "epicfight:impact");
        if (weight > 100){
            weight = 100;
        }
        float amount = 0.5f + impact - (impact * weight / 100 );
        StaminaUtils.StaminaConsume(ent, amount);
    }
}
