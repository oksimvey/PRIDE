package com.robson.pride.events;

import com.robson.pride.api.customtick.CustomTickManager;
import com.robson.pride.api.musiccore.PrideMusicManager;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.api.utils.TagsUtils;
import com.robson.pride.progression.NewCap;
import com.robson.pride.progression.PlayerAttributeSetup;
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
            player.getPersistentData().putBoolean("isParrying", false);
            playerCommonSetup(player);
            CustomTickManager.startTick(player);
            TagsUtils.playerTags.put(player, player.getPersistentData());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            CustomTickManager.startRespawnTick(player);
            PlayerAttributeSetup.setupPlayerAttributes(player);
            playerCommonSetup(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            CustomTickManager.stopTick(player);
            PrideMusicManager.playerMusicManagerThread.remove(player);
            player.getPersistentData().remove("pride:cooldown_skills");
        }
    }

    @SubscribeEvent
    public static void clonePlayer(PlayerEvent.Clone event) {
        event.getOriginal().revive();
        TagsUtils.playerTags.remove(event.getOriginal());
        TagsUtils.playerTags.put(event.getOriginal(), event.getEntity().getPersistentData());
        CompoundTag originaltag = event.getOriginal().getPersistentData();
        CompoundTag clonetag = event.getEntity().getPersistentData();
        NewCap.setupVariables(originaltag, clonetag);
    }

    public static void playerCommonSetup(Player player) {
        if (player != null) {
            CompoundTag tag = player.getPersistentData();
            StaminaUtils.resetStamina(player);
            if (!NewCap.haveVariables(tag)) {
                NewCap.startVariables(player, tag);
            }
        }
    }

}
