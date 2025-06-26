package com.robson.pride.events;

import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class onRClickItem {


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

