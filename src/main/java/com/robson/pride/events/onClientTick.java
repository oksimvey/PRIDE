package com.robson.pride.events;

import com.robson.pride.api.client.RenderingCore;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mod.EventBusSubscriber
public class onClientTick {
    private static int tickcount = 0;

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        RenderingCore.renderCore();
        Player player = Minecraft.getInstance().player;
        if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND) && player.isUsingItem()) {
            player.setSprinting(false);
        }
    }

    public static void playerAnim(Player player) {
        if (player != null) {
            PlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch != null) {
                if (playerPatch.getAnimator() instanceof ClientAnimator aniamtor) {
                    if (aniamtor.currentCompositeMotion() == LivingMotions.IDLE) {
                        if (tickcount == 0) {
                            aniamtor.playAnimation(WOMAnimations.SOLAR_OBSCURIDAD_IDLE, 0.1f);
                        }
                        tickcount++;
                        if (tickcount == 20) {
                            tickcount = 0;
                        }
                    }
                    if (aniamtor.currentCompositeMotion() == LivingMotions.WALK) {
                        if (tickcount == 0) {
                            aniamtor.playAnimation(WOMAnimations.SOLAR_OBSCURIDAD_WALK, 0.1f);
                        }
                        if (tickcount == 20) {
                            tickcount = 0;
                        }
                        tickcount++;
                    }
                }
            }
        }
    }
}

