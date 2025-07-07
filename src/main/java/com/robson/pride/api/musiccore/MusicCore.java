package com.robson.pride.api.musiccore;
import com.robson.pride.api.data.manager.BiomeDataManager;
import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.math.MathUtils;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

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

    public static Music deserializeMobMusic(Player player, PrideMusicManager musicManager){
        boolean fighting = false;
        for (Entity ent : player.level().getEntities(player, MathUtils.createAABBAroundEnt(player, 50))) {
            if (TargetUtil.getTarget(ent) == player) {
                fighting = true;
                break;
            }
        }
        return deserializeBiomeMusic(player, fighting);
    }

    public static Music deserializeBiomeMusic(Player player, boolean isFightning) {
        if (player != null) {
            String biome = player.level().getBiome(player.blockPosition()).unwrapKey().get().location().toString();
            if (!isFightning && BiomeDataManager.BIOME_MUSICS.get(biome) != null) {
                return BiomeDataManager.BIOME_MUSICS.get(biome);
            }
            else if (BiomeDataManager.BIOME_BATTLE_MUSICS.get(biome) != null) {
                return BiomeDataManager.BIOME_BATTLE_MUSICS.get(biome);
            }
        }
        return null;
    }
}
