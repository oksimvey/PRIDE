package com.robson.pride.api.cam;

import com.github.leawind.thirdperson.ThirdPerson;
import com.github.leawind.thirdperson.config.AbstractConfig;
import com.github.leawind.thirdperson.config.Config;
import com.robson.pride.api.customtick.CustomTickManager;
import com.robson.pride.api.utils.CameraUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class DynamicCam {

    public static void dynamicCamTick(Player player){
        if (player != null){
            Config config = ThirdPerson.getConfig();
            if (player.getVehicle() == null && CustomTickManager.targeting_entities.get(player) != null && !(CustomTickManager.targeting_entities.get(player).isEmpty())) {
                if (config.normal_rotate_mode != AbstractConfig.PlayerRotateMode.PARALLEL_WITH_CAMERA){
                    config.normal_rotate_mode = AbstractConfig.PlayerRotateMode.PARALLEL_WITH_CAMERA;
                }
                for (Entity ent : CustomTickManager.targeting_entities.get(player)) {
                    if (ent != null && ent.getBbHeight() / 100 > config.normal_offset_y) {
                        CameraUtils.changeCamOffset(0, 0 + ent.getBbHeight() / 100, getDefaultZModifier(player) + 0.25f + ent.getBbHeight() / 5);
                    }
                }
            }
            else {
                float zmodifier = getDefaultZModifier(player);
                CameraUtils.changeCamOffset(-0.12f + (zmodifier / 50), -0.02f, zmodifier);
                if (config.normal_rotate_mode != AbstractConfig.PlayerRotateMode.INTEREST_POINT){
                    config.normal_rotate_mode = AbstractConfig.PlayerRotateMode.INTEREST_POINT;
                }
            }
        }
    }

     public static float getDefaultZModifier(Player player){
            return player.isUsingItem() &&
                    (ItemStackUtils.getWeaponCategory(player, InteractionHand.MAIN_HAND) == WeaponCategoriesEnum.PRIDE_GUN ||
                            ItemStackUtils.getStyle(player) == PrideStyles.GUN_OFFHAND ||
                            ItemStackUtils.getWeaponCategory(player, InteractionHand.MAIN_HAND) == CapabilityItem.WeaponCategories.RANGED)?
                    1 : (float) ((1 + ItemStackUtils.getColliderSize(player.getMainHandItem()) / 2) + ((player.getDeltaMovement().x + player.getDeltaMovement().z) * 2));
     }
}
