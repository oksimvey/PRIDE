package com.robson.pride.events;

import com.robson.pride.api.mechanics.Parry;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.epicfight.styles.SheatProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class onRClickItem {

    @SubscribeEvent
    public static void onRClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity() != null) {
            Player player = event.getEntity();
            if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND)) {
                player.startUsingItem(InteractionHand.MAIN_HAND);
                Parry.ParryWindow(player);
            }
            if (ItemStackUtils.checkShield(player, InteractionHand.MAIN_HAND) || ItemStackUtils.checkShield(player, InteractionHand.OFF_HAND)) {
                Parry.ParryWindow(player);
                shieldEffect(player);
            }
            SheatProvider.unsheat(player);
        }
    }

    private static boolean isBlockingWithShield(Player player) {
        return player != null && player.isUsingItem() && (ItemStackUtils.checkShield(player, InteractionHand.MAIN_HAND) || ItemStackUtils.checkShield(player, InteractionHand.OFF_HAND));
    }

    private static void shieldEffect(Player player) {
        if (player != null) {
            TimerUtil.schedule(() -> {
                if (isBlockingWithShield(player)) {
                    shieldEffect(player);
                }
            }, 500, TimeUnit.MILLISECONDS);
            if (player.hasEffect(EpicFightMobEffects.STUN_IMMUNITY.get())) {
                player.removeEffect(EpicFightMobEffects.STUN_IMMUNITY.get());
            } else player.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 10));
        }
    }
}

