package com.robson.pride.api.utils;

import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public class ItemStackUtils {

    public static WeaponCategory getWeaponCategory (Entity ent, InteractionHand hand){
        LivingEntityPatch livingEntityPatch  = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null ){
                if (livingEntityPatch.getHoldingItemCapability(hand) != null){
                    return livingEntityPatch.getHoldingItemCapability(hand).getWeaponCategory();
                }
            }
        return null;
    }

    public static Style getStyle(Entity ent){
        LivingEntityPatch livingEntityPatch  = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null ){
            if (livingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND) != null){
                return livingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(livingEntityPatch);
            }
        }
        return null;
    }

    public static boolean checkWeapon(Entity ent, InteractionHand hand){
        if (ent != null){
            WeaponCategory category = getWeaponCategory(ent, hand);
                if (category != null){
                    return category == WeaponCategoriesEnum.PRIDE_LONGSWORD;
            }
        }
        return false;
    }

    public static boolean checkShield(Entity ent, InteractionHand hand){
        if (ent != null){
            WeaponCategory category = getWeaponCategory(ent, hand);
            if (category != null){
                return category == CapabilityItem.WeaponCategories.SHIELD;
            }
        }
        return false;
    }

    public static String checkBlockType(Entity ent){
        String BlockType = "";
        if (checkShield(ent, InteractionHand.MAIN_HAND)) {
            BlockType = "mainhandshield";
        } else {
            if (checkShield(ent, InteractionHand.OFF_HAND)) {
                BlockType = "offhandshield";
            } else {
                if (checkWeapon(ent, InteractionHand.MAIN_HAND)) {
                    BlockType = "weapon";
                }
            }
        }
        return BlockType;
    }
}
