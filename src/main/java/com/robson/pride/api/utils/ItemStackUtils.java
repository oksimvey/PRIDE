package com.robson.pride.api.utils;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.List;

public class ItemStackUtils {

    public static Style getStyle(LivingEntityPatch ent, WeaponCategory weaponCategory){
        if (ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) {
            return PrideStyles.SHIELD_OFFHAND;
        }
        else if (ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == weaponCategory) {
            return PrideStyles.DUAL_WIELD;
        }
        else if (ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN && ControllEngine.isKeyDown(EpicFightKeyMappings.GUARD) && ControllEngine.isKeyDown(EpicFightKeyMappings.ATTACK)) {
            return CapabilityItem.Styles.RANGED;
        }
        else if (ent.getOriginal() instanceof LivingEntity lent && lent.getMainHandItem().getTag() != null && lent.getMainHandItem().getTag().getBoolean("two_handed") && ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
            return CapabilityItem.Styles.TWO_HAND;
        }
        return CapabilityItem.Styles.ONE_HAND;
    }

    public static WeaponCategory getWeaponCategory(Entity ent, InteractionHand hand) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            if (livingEntityPatch.getHoldingItemCapability(hand) != null) {
                return livingEntityPatch.getHoldingItemCapability(hand).getWeaponCategory();
            }
        }
        return null;
    }

    public static List<AnimationProvider<?>> getWeaponMotions(ItemStack weapon){
        if (weapon != null && Minecraft.getInstance().player != null){
            CapabilityItem capabilityItem = EpicFightCapabilities.getItemStackCapability(weapon);
            if (capabilityItem != null){
                if (capabilityItem instanceof WeaponCapability weaponCapability){
                    return weaponCapability.getAutoAttckMotion(EpicFightCapabilities.getEntityPatch(Minecraft.getInstance().player, PlayerPatch.class));
                }
            }
        }
        return null;
    }

    public static Style getStyle(Entity ent) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            if (livingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND) != null) {
                return livingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(livingEntityPatch);
            }
        }
        return CapabilityItem.Styles.COMMON;
    }

    public static float getWeaponWeight(Entity ent, InteractionHand hand, EquipmentSlot slot) {
        if (ent != null) {
            if (ent instanceof LivingEntity living) {
                ItemStack itemStack;
                if (hand == InteractionHand.MAIN_HAND) {
                    itemStack = living.getMainHandItem();
                } else itemStack = living.getOffhandItem();
                CompoundTag tag = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(itemStack.getItem());
                if (tag != null) {
                    if (tag.contains("attributes")) {
                        CompoundTag attributes = tag.getCompound("attributes");
                        for (String key : attributes.getAllKeys()) {
                            if (attributes.getCompound(key).contains("weight")) {
                                return (float) attributes.getCompound(key).getDouble("weight");
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static InteractionHand checkAttackingHand(Entity ent) {
        if (ent != null) {
            if (getStyle(ent) == PrideStyles.DUAL_WIELD) {
                return TagsUtils.toggleBoolean(ent.getPersistentData(), "attacking_hand_toggle") ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            }
        }
        return InteractionHand.MAIN_HAND;
    }

    public static boolean checkWeapon(Entity ent, InteractionHand hand) {
        if (ent != null) {
            if (ent instanceof LivingEntity living) {
                ItemStack itemStack = switch (hand) {
                    case MAIN_HAND -> living.getMainHandItem();
                    case OFF_HAND -> living.getOffhandItem();
                };
                CompoundTag tag = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(itemStack.getItem());
                if (tag != null) {
                    return tag.contains("isWeapon") && tag.getBoolean("isWeapon");
                }
            }
        }
        return false;
    }


    public static boolean checkShield(Entity ent, InteractionHand hand) {
        if (ent != null) {
            WeaponCategory category = getWeaponCategory(ent, hand);
            if (category != null) {
                return category == CapabilityItem.WeaponCategories.SHIELD;
            }
        }
        return false;
    }

    public static String checkBlockType(Entity ent) {
        if (checkShield(ent, InteractionHand.MAIN_HAND)) {
            return "mainhandshield";
        }
        if (checkShield(ent, InteractionHand.OFF_HAND)) {
            return "offhandshield";
        }
        if (checkWeapon(ent, InteractionHand.MAIN_HAND)) {
            return "weapon";
        }
        return "";
    }
}
