package com.robson.pride.api.utils;

import com.sun.management.OperatingSystemMXBean;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import java.lang.management.ManagementFactory;

public interface LodTick {

    OperatingSystemMXBean system = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    static boolean canTick(Entity entity, float multiplier){
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && entity != null) {
            short distance = (short) (1 + (Math.pow(1.075, client.gameRenderer.getMainCamera().getPosition().distanceTo(entity.position())) *
                    multiplier * (Math.min(system.getCpuLoad() * 10f, 1))));
            return client.player.tickCount % distance == 0;
        }
        return false;
    }

}
