package com.robson.pride.events;

import com.robson.pride.api.mechanics.MusicCore;
import com.robson.pride.api.utils.ClientPlayerTagsAcessor;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.progression.NewCap;
import com.robson.pride.progression.PlayerAttributeSetup;
import net.minecraft.client.Minecraft;
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
            playerCommonSetup(player);
            ClientPlayerTagsAcessor.playerTags.put(player, player.getPersistentData());
            Minecraft client = Minecraft.getInstance();
            MusicCore.musicManagerMap.put(player, client.getMusicManager());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            PlayerAttributeSetup.setupPlayerAttributes(player);
            playerCommonSetup(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (player != null) {

        }
    }

    @SubscribeEvent
    public static void clonePlayer(PlayerEvent.Clone event) {
        event.getOriginal().revive();
        CompoundTag originaltag = event.getOriginal().getPersistentData();
        CompoundTag clonetag = event.getEntity().getPersistentData();
        NewCap.setupVariables(originaltag, clonetag);
    }

    public static void playerCommonSetup(Player player) {
        if (player != null) {
            CompoundTag tag = player.getPersistentData();
            StaminaUtils.resetStamina(player);
            player.getPersistentData().putBoolean("isParrying", false);
            if (!NewCap.haveVariables(tag)) {
                NewCap.startVariables(player, tag);
            }
        }
    }
}
