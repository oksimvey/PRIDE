package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public interface LodTick {

    static boolean canTick(Entity entity, float multiplier){
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && entity != null) {
            short distance = (short) (1 + (Math.pow(1.075, client.gameRenderer.getMainCamera().getPosition().distanceTo(entity.position())) * multiplier));
            return client.player.tickCount % distance == 0;
        }
        return false;
    }
}
