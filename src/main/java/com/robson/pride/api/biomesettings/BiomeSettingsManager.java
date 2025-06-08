package com.robson.pride.api.biomesettings;

import com.robson.pride.api.utils.PlaySoundUtils;
import net.minecraft.world.entity.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeSettingsManager {

    public static Map<String, BiomeSettings> biomeSettingsMap = new HashMap<>();

    public static void register() {
        biomeSettingsMap.put("minecraft:plains", new BiomeSettings(PlaySoundUtils.getMusicByString("pride:plains_music"),
                List.of(new EntityTypeSpawnSetting(EntityType.IRON_GOLEM, (byte) 5, (byte) 0))));
    }
}
