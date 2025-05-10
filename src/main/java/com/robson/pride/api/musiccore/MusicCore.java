package com.robson.pride.api.musiccore;

import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.player.Player;

public class MusicCore {

    public static void musicCore(Player player) {
       if (player != null){
           PrideMusicManager prideMusicManager = PrideMusicManager.playerMusicManagerThread.get(player);
           MusicManager musicManager = prideMusicManager.getMusicManager();
           Music music = deserializeMobMusic(player, prideMusicManager);
           if (music != null){
               if (!musicManager.isPlayingMusic(music)){
                   musicManager.stopPlaying();
                   musicManager.startPlaying(music);
               }
           }
           else musicManager.stopPlaying();
       }
    }



    public static Music deserializeMobMusic(Player player, PrideMusicManager musicManager) {
        if (player != null) {

        }
        return deserializeBiomeMusic(player);
    }

    public static Music deserializeBiomeMusic(Player player){
        return null;
    }
}
