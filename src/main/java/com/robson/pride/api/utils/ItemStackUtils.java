package com.robson.pride.api.utils;

import com.robson.pride.api.data.manager.WeaponDataManager;
import com.robson.pride.api.data.types.item.WeaponData;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import com.robson.pride.epicfight.weapontypes.WeaponPresets;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import static com.robson.pride.keybinding.KeySwapHand.addModifierToStyle;


public class ItemStackUtils {

    public static Style getStyle(LivingEntityPatch ent, WeaponCategory weaponCategory) {
        if (ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) {
            return PrideStyles.SHIELD_OFFHAND;
        }
        else if (ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == weaponCategory) {
            return PrideStyles.DUAL_WIELD;
        }
        else if (ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN && ent.getOriginal() instanceof LivingEntity lent && lent.getMainHandItem().getTag() != null && lent.getMainHandItem().getTag().getBoolean("two_handed")) {
            return PrideStyles.GUN_OFFHAND;
        }
        else if (ent.getOriginal() instanceof LivingEntity lent && WeaponPresets.WIELDING_TWO_HAND.contains(lent) && ent.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
            return CapabilityItem.Styles.TWO_HAND;
        }
        return CapabilityItem.Styles.ONE_HAND;
    }

    public static void swapHand(LivingEntity ent){
        if (WeaponPresets.WIELDING_TWO_HAND.contains(ent)) {
            WeaponPresets.WIELDING_TWO_HAND.remove(ent);
        }
        else WeaponPresets.WIELDING_TWO_HAND.add(ent);
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            livingEntityPatch.updateHeldItem(livingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND), livingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND), ent.getMainHandItem(), ent.getMainHandItem(), InteractionHand.MAIN_HAND);
        }
        if (ent instanceof Player player) {
            addModifierToStyle(player);
        }
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
            WeaponData data = WeaponDataManager.MANAGER.getByItem(item);
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

    public static float getWeaponWeight(ItemStack item) {
        if (item != null) {
                WeaponData weaponData = WeaponDataManager.MANAGER.getByItem(item);
                if (weaponData != null) {
                    return  weaponData.getWeight();
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
                return WeaponDataManager.MANAGER.getByItem(itemStack) != null;
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
}
