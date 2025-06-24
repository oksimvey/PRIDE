package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public interface LodTick {

    static boolean canTick(Vec3 pos, float multiplier){
        Minecraft client = Minecraft.getInstance();
        if (client.gameRenderer.getMainCamera().getPosition() != null) {
            short distance = (short) (1 + Math.pow(1.025, pos.distanceTo(client.player.position())) * multiplier);
            return client.player.tickCount % distance == 0;
        }
        return false;
    }

    static boolean canTick(Entity entity, float multiplier){
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && entity != null) {
            short distance = (short) (1 + Math.pow(1.025, client.gameRenderer.getMainCamera().getPosition().distanceTo(entity.position())) * multiplier);
            return client.player.tickCount % distance == 0;
        }
        return false;
    }

}
