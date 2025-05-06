package com.robson.pride.epicfight.styles;

import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class SheatProvider {

    private static byte idlecounter = 0;

    public static void provideSheat(Player player){
        if (ItemStackUtils.getWeaponCategory(player, InteractionHand.MAIN_HAND) != WeaponCategoriesEnum.PRIDE_KATANA || ItemStackUtils.getStyle(player) != CapabilityItem.Styles.ONE_HAND || player.getPersistentData().getBoolean("pride_sheat")){
            return;
        }
       PlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (playerPatch != null) {
            if (playerPatch.getCurrentLivingMotion() == LivingMotions.IDLE) {
                idlecounter++;
                if (idlecounter >= 8) {
                    idlecounter = 0;
                        player.getPersistentData().putBoolean("pride_sheat", true);
                        AnimUtils.playAnim(player, Animations.BIPED_UCHIGATANA_SCRAP, 0.1f);
                        playerPatch.updateHeldItem(playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND), playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND), player.getMainHandItem(), player.getMainHandItem(), InteractionHand.MAIN_HAND);
                }
            }
        }
    }

    public static void unsheat(Player player){
        if (player != null){
            player.getPersistentData().remove("pride_sheat");
            PlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            playerPatch.updateHeldItem(playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND), playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND), player.getMainHandItem(), player.getMainHandItem(), InteractionHand.MAIN_HAND);
        }
    }
}
