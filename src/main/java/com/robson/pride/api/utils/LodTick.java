package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public interface LodTick {

    default boolean canTick(Vec3 pos, float multiplier){
        Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            short distance = (short) (1 + Math.pow(1.025, pos.distanceTo(client.player.position())) * multiplier);
            return client.player.tickCount % distance == 0;
        }
        return false;
    }

    default boolean canTick(Entity entity, float multiplier){
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && entity != null) {
            short distance = (short) (1 + Math.pow(1.025, entity.distanceTo(client.player)) * multiplier);
            return client.player.tickCount % distance == 0;
        }
        return false;
    }
}
