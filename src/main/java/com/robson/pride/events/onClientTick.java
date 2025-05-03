package com.robson.pride.events;

import com.robson.pride.api.client.RenderingCore;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.epicfight.styles.SheatProvider;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mod.EventBusSubscriber
public class onClientTick {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null) {
            RenderingCore.renderCore();
            LocalPlayer player = Minecraft.getInstance().player;
            if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND) && player.isUsingItem()) {
                player.setSprinting(false);
            }
            if (ItemStackUtils.getWeaponCategory(player, InteractionHand.MAIN_HAND) == (WeaponCategoriesEnum.PRIDE_KATANA)){
                SheatProvider.provideSheat(player);
            }
        }
    }

    public static void changeLivingMotion(Entity ent) {
        if (ent != null) {
            LivingEntityPatch entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null) {
                if (entityPatch.getAnimator() instanceof ClientAnimator animator) {
                    animator.addLivingAnimation(LivingMotions.IDLE, WOMAnimations.SOLAR_OBSCURIDAD_IDLE);
                }
            }
        }
    }
}

