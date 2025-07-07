package com.robson.pride.api.data.biome;

import com.robson.pride.api.utils.math.PrideVec3f;

import java.util.List;

public class SpawnSpots {

    private final List<PrideVec3f> spots;

    private final SpawnConfig config;

    public SpawnSpots(List<PrideVec3f> spots, SpawnConfig config) {
        this.spots = spots;
        this.config = config;
    }
}
