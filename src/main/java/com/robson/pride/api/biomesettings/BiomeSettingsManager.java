package com.robson.pride.api.biomesettings;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BiomeSettingsManager {

    public static Map<ResourceKey<Biome>, BiomeSettings> biomeMap = new HashMap<>();

    public static void register(){
        biomeMap.put(Biomes.PLAINS,
                new BiomeSettings(new Music(Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation("pride:plains_music"))), 1, 1, true),
                        null));
    }
}
