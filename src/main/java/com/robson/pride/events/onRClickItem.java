package com.robson.pride.events;

import com.robson.pride.api.mechanics.Parry;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.CommandUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.skills.weaponarts.DarknessCut;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.client.particle.AntitheusBlackholeStartParticle;
import reascer.wom.particle.WOMParticles;

import static com.robson.pride.api.skillcore.WeaponSkillBase.Skills;

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
        }
    }
}

