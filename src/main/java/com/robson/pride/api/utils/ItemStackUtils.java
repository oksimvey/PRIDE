package com.robson.pride.api.utils;

import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.List;
import java.util.Random;

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

    public static InteractionHand getWeaponSpeed(Entity ent, InteractionHand hand){
        if (ent != null){
            LivingEntityPatch livingEntityPatch  = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null ){
               if (livingEntityPatch.getHoldingItemCapability(hand) != null){
                }
            }
        }
        return InteractionHand.MAIN_HAND;
    }

    public static float getWeaponWeight(Entity ent, InteractionHand hand, EquipmentSlot slot){
        if (ent != null){
            if (ent instanceof LivingEntity  living) {
                LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(living, LivingEntityPatch.class);
                if (livingEntityPatch != null) {
                    ItemStack itemStack;
                    if (hand == InteractionHand.MAIN_HAND) {
                        itemStack = living.getMainHandItem();
                    } else itemStack = living.getOffhandItem();
                    List<AttributeModifier> modifiers = CapabilityItem.getAttributeModifiers(
                            EpicFightAttributes.MAX_STRIKES.get(),
                            slot,
                            itemStack,
                            livingEntityPatch
                    );
                    float total = 0.0f;
                    for (AttributeModifier modifier : modifiers) {
                        total += (float) modifier.getAmount();
                    }
                    return total;
                }
            }
        }
        return 0;
    }

    public static InteractionHand checkAttackingHand(Entity ent){
        if (ent != null){
            if (getStyle(ent) == PrideStyles.DUAL_WIELD){
                Random random  = new Random();
                if (random.nextBoolean()){
                    return InteractionHand.MAIN_HAND;
                }
                else return InteractionHand.OFF_HAND;
            }
        }
        return InteractionHand.MAIN_HAND;
    }

    public static boolean checkWeapon(Entity ent, InteractionHand hand){
        if (ent != null){
            WeaponCategory category = getWeaponCategory(ent, hand);
                if (category != null){
                    return category == WeaponCategoriesEnum.PRIDE_LONGSWORD
                    || category == CapabilityItem.WeaponCategories.TACHI;
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
