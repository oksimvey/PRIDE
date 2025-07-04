package com.robson.pride.api.musiccore;
import com.robson.pride.api.customtick.PlayerCustomTick;
import com.robson.pride.api.data.manager.BiomeDataManager;
import com.robson.pride.api.data.player.ClientDataManager;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import static com.robson.pride.api.data.player.ClientDataManager.CLIENT_DATA_MANAGER;

public class MusicCore {

    public static void musicCore(Player player) {
        if (player != null) {
            PrideMusicManager prideMusicManager = ClientDataManager.CLIENT_DATA_MANAGER.get(player).getMusicManager();
            MusicManager musicManager = prideMusicManager.getMusicManager();
            Music music = deserializeMobMusic(player, prideMusicManager);
            if (music == null) {
                musicManager.stopPlaying();
                return;
            }
            if (!musicManager.isPlayingMusic(music)) {
                musicManager.stopPlaying();
                musicManager.startPlaying(music);
            }
        }
    }

    public static Music deserializeMobMusic(Player player, PrideMusicManager musicManager) {
            for (LivingEntity ent : CLIENT_DATA_MANAGER.get(player).getTargetingEntities()) {

            }
            musicManager.setCurrentMusicPriority((byte) 0);
        return deserializeBiomeMusic(player);
    }

    public static Music deserializeBiomeMusic(Player player) {
        if (player != null) {
            String biome = player.level().getBiome(player.blockPosition()).unwrapKey().get().location().toString();
            if (BiomeDataManager.BIOME_MUSICS.get(biome) != null) {
                return BiomeDataManager.BIOME_MUSICS.get(biome);
            }
        }
        return null;
    }
}
