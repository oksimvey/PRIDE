package com.robson.pride.api.skillcore;

import net.minecraft.world.entity.player.Player;

import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {

    public static ConcurrentHashMap<Player, ConcurrentHashMap<String, Short>> playerSkillsCooldown = new ConcurrentHashMap<>();

    public static void timeCooldowns(Player player) {
        if (player != null && playerSkillsCooldown.get(player) != null) {
            for (String entry : playerSkillsCooldown.get(player).keySet()) {
                short value = playerSkillsCooldown.get(player).get(entry);
                playerSkillsCooldown.get(player).put(entry, (short) (value - 1));
                if (value <= 0) {
                    playerSkillsCooldown.get(player).remove(entry);
                }
            }
        }
    }
}
