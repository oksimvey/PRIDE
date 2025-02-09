package com.robson.pride.api.musiccore;

import net.minecraft.client.sounds.MusicManager;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.ConcurrentHashMap;

public class PrideMusicManager {

    public static ConcurrentHashMap<Player, PrideMusicManager> playerMusicManagerThread = new ConcurrentHashMap<>();

    private byte currentMusicPriority;

    private MusicManager musicManager;

    public PrideMusicManager(byte currentMusicPriority, MusicManager musicManager){
        this.currentMusicPriority = currentMusicPriority;
        this.musicManager = musicManager;
    }

    public MusicManager getMusicManager() {
        return this.musicManager;
    }

    public void setMusicManager(MusicManager musicManager) {
        this.musicManager = musicManager;
    }

    public byte getCurrentMusicPriority() {
        return this.currentMusicPriority;
    }

    public void setCurrentMusicPriority(byte currentMusicPriority) {
        this.currentMusicPriority = currentMusicPriority;
    }
}
