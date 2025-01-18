package com.robson.pride.events;

import com.robson.pride.api.utils.CustomTick;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.progression.NewCap;
import com.robson.pride.progression.ProgressionGUIRender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerSetup {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            CustomTick.startTick(player);
            playerCommonSetup(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            CustomTick.startRespawnTick(player);
            playerCommonSetup(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            CustomTick.stopTick(player);
        }
    }

    @SubscribeEvent
    public static void clonePlayer(PlayerEvent.Clone event) {
        event.getOriginal().revive();
        CompoundTag originaltag = event.getOriginal().getPersistentData();
        CompoundTag clonetag = event.getEntity().getPersistentData();
        NewCap.setupVariables(originaltag, clonetag);
    }

        public static void playerCommonSetup(Player player){
        if (player != null){
            CompoundTag tag = player.getPersistentData();
            StaminaUtils.resetStamina(player);
            player.getPersistentData().putBoolean("isParrying", false);
            ProgressionGUIRender.playertags.put(player, tag);
            if (!NewCap.haveVariables(tag)){
                NewCap.startVariables(player, tag);
            }
        }
    }
}
