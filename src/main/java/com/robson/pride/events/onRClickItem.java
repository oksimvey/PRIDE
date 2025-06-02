package com.robson.pride.events;

import com.robson.pride.api.mechanics.Parry;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.epicfight.styles.SheatProvider;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.client.renderer.shader.AnimationShaderInstance;
import yesman.epicfight.client.renderer.shader.IrisAnimationShader;

import java.util.List;

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
            }
            SheatProvider.unsheat(player);
        }
    }
}

