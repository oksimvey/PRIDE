package com.robson.pride.api.biomesettings;

import net.minecraft.world.entity.EntityType;

public class EntityTypeSpawnSetting {

    private EntityType entityType;

    private byte maxEntities;

    private byte weight;

    private boolean rain;

    public EntityTypeSpawnSetting(EntityType type, byte maxEntities, byte weight){
       this.entityType = type;
       this.maxEntities = maxEntities;
       this.weight = weight;
    }

    public EntityType getEntityType(){
        return this.entityType;
    }

    public byte getMaxEntities(){
        return this.maxEntities;
    }

    public byte getWeight(){
        return this.weight;
    }

}
