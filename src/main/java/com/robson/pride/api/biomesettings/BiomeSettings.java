package com.robson.pride.api.biomesettings;

import net.minecraft.sounds.Music;

import java.util.List;

public class BiomeSettings {

    private Music biomeMusic;

    private List<EntityTypeSpawnSetting> entitiesSpawnSettings;

    public BiomeSettings(Music music, List<EntityTypeSpawnSetting> entitiesSpawnSettings){
        this.biomeMusic = music;
        this.entitiesSpawnSettings = entitiesSpawnSettings;
    }

    public Music getBiomeMusic(){
        return this.biomeMusic;
    }

    public List<EntityTypeSpawnSetting> getEntitiesSpawnSettings(){
        return this.entitiesSpawnSettings;
    }
}
