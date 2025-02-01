package com.robson.pride.api.utils;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ItemStackUtils {

    private static boolean toggle = false;

    public static ConcurrentHashMap<Entity, ListTag> storePreviousItems = new ConcurrentHashMap<>();

    private static List<EquipmentSlot> Slots = Arrays.asList(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

    public static void storeItem(Entity ent, EquipmentSlot slot) {
        if (ent != null) {
            if (ent instanceof LivingEntity living) {
                ItemStack itemstostore = null;

            }
        }
    }

    public static void equipOldItems(Entity ent){
        if (ent != null){
            if (ent instanceof LivingEntity living){

            }
        }
    }

    public static void equipSpecificOldItem(Entity ent, String slot){}

    public static WeaponCategory getWeaponCategory(Entity ent, InteractionHand hand) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            if (livingEntityPatch.getHoldingItemCapability(hand) != null) {
                return livingEntityPatch.getHoldingItemCapability(hand).getWeaponCategory();
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

    public static InteractionHand getWeaponSpeed(Entity ent, InteractionHand hand) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                if (livingEntityPatch.getHoldingItemCapability(hand) != null) {
                }
            }
        }
        return InteractionHand.MAIN_HAND;
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
                            if(attributes.getCompound(key).contains("weight")){
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
                toggle = !toggle;
                    return toggle ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            }
        }
        return InteractionHand.MAIN_HAND;
    }

    public static boolean checkWeapon(Entity ent, InteractionHand hand) {
        if (ent != null) {
            WeaponCategory category = getWeaponCategory(ent, hand);
            if (category != null) {
                return category == WeaponCategoriesEnum.PRIDE_LONGSWORD
                        || category == CapabilityItem.WeaponCategories.TACHI
                        || category == WeaponCategoriesEnum.PRIDE_FIGHTNING_STYLE;
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
