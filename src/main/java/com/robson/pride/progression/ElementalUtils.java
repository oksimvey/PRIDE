package com.robson.pride.progression;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicReference;

public class ElementalUtils {

    public static void setElement(Entity ent, String element) {
        if (ent instanceof Player player) {
            player.getCapability(PlayerProgressionData.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                capability.setElement(element);
            });
        }
    }

    public static String getElement(Entity ent) {
        AtomicReference<String> element = new AtomicReference<>("");
        if (ent instanceof Player player) {
            player.getCapability(PlayerProgressionData.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                element.set(capability.getElement());
            });
        }
        return element.get();
    }
}
