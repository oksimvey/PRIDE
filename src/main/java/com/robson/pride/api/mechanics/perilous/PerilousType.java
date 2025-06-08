package com.robson.pride.api.mechanics.perilous;

import com.robson.pride.api.mechanics.mikiricounter.MikiriType;
import net.minecraft.world.entity.Entity;

import java.util.concurrent.ConcurrentHashMap;

public class PerilousType {

    private static ConcurrentHashMap<Entity, PerilousType> perilousTypeMap = new ConcurrentHashMap<>();

    private MikiriType mikiriType;

    public PerilousType(MikiriType mikiriType) {
        this.mikiriType = mikiriType;
    }

    public MikiriType getMikiriType() {
        return this.mikiriType;
    }

    public boolean isPerilous(Entity entity) {
        return entity != null && perilousTypeMap.get(entity) != null;
    }

    public PerilousType getEntityPerilous(Entity entity) {
        if (entity != null) {
            return perilousTypeMap.get(entity);
        }
        return null;
    }
}
