package com.robson.pride.api.maps;

import com.robson.pride.api.utils.PlaySoundUtils;
import net.minecraft.sounds.Music;

import java.util.Map;

public interface BiomeMusics {

    Map<String, Music> BIOME_MUSICS = Map.ofEntries(
            Map.entry("minecraft:plains", PlaySoundUtils.getMusicByString("pride:plains_music"))
    );
}
