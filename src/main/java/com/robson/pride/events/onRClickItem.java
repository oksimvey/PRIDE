package com.robson.pride.events;

import com.github.leawind.thirdperson.ThirdPerson;
import com.github.leawind.thirdperson.api.client.event.ThirdPersonCameraSetupEvent;
import com.robson.pride.api.mechanics.Parry;
import com.robson.pride.api.utils.CameraUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
            if (ItemStackUtils.checkShield(player, InteractionHand.MAIN_HAND) || ItemStackUtils.checkShield(player, InteractionHand.OFF_HAND)){
                Parry.ParryWindow(player);
            }
        }
    }
}

