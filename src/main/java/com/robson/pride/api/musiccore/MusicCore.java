package com.robson.pride.api.musiccore;

import com.robson.pride.api.biomesettings.BiomeSettingsManager;
import com.robson.pride.api.customtick.CustomTickManager;
import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class MusicCore {

    public static void musicCore(Player player) {
       if (player != null){
           PrideMusicManager prideMusicManager = PrideMusicManager.playerMusicManagerThread.get(player);
           MusicManager musicManager = prideMusicManager.getMusicManager();
           Music music = deserializeMobMusic(player, prideMusicManager);
           if (music != null && !musicManager.isPlayingMusic(music)){
                   musicManager.stopPlaying();
                   musicManager.startPlaying(music);
           }
           else musicManager.stopPlaying();
       }
    }

    public static Music deserializeMobMusic(Player player, PrideMusicManager musicManager) {
        if (player != null && CustomTickManager.targeting_entities.get(player) != null) {
            for (Entity ent : CustomTickManager.targeting_entities.get(player)) {
                if (ent instanceof PrideMobBase prideMobBase) {
                    if (prideMobBase.getMusicPriority() >= musicManager.getCurrentMusicPriority()) {
                        musicManager.setCurrentMusicPriority(prideMobBase.getMusicPriority());
                        return prideMobBase.getMobMusic();
                    }
                }
            }
            musicManager.setCurrentMusicPriority((byte) 0);
        }
        return deserializeBiomeMusic(player);
    }

    public static Music deserializeBiomeMusic(Player player){
        if (player != null){
            ResourceKey<Biome> biome = player.level().getBiome(player.blockPosition()).unwrapKey().orElseThrow();
            if (BiomeSettingsManager.biomeMap.get(biome) != null){
                return BiomeSettingsManager.biomeMap.get(biome).getBiomeMusic();
            }
        }
        return null;
    }
}
