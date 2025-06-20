package com.robson.pride.api.utils;

import com.robson.pride.api.data.item.ItemData;
import com.robson.pride.api.data.item.WeaponData;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public class ItemStackUtils {

    public static Style getStyle(LivingEntityPatch ent, WeaponCategory weaponCategory) {
        if (ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) {
            return PrideStyles.SHIELD_OFFHAND;
        } else if (ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == weaponCategory) {
            return PrideStyles.DUAL_WIELD;
        } else if (ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN && ent.getOriginal() instanceof LivingEntity lent && lent.getMainHandItem().getTag() != null && lent.getMainHandItem().getTag().getBoolean("two_handed")) {
            return PrideStyles.GUN_OFFHAND;
        } else if (ent.getOriginal() instanceof LivingEntity lent && lent.getMainHandItem().getTag() != null && lent.getMainHandItem().getTag().getBoolean("two_handed") && ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
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

    public static float getColliderSize(ItemStack item) {
        if (item != null) {
            ItemData data = ItemData.getItemData(item);
            if (data != null) {
                Matrix2f collider = data.getCollider();
                if (collider != null) {
                    return MathUtils.getTotalDistance(collider.x1() + collider.x0(), collider.y1() + collider.y0(), collider.z1() + collider.z0());

                }
            }
        }
        return 1;
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
                WeaponData weaponData = WeaponData.getWeaponData(itemStack);
                if (weaponData != null) {
                    return  weaponData.getWeight();
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
                return WeaponData.getWeaponData(itemStack) != null;
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
